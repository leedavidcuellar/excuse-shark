package com.excusasshark.service;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.model.Excuse;
import com.excusasshark.repository.ExcuseRepository;
import com.excusasshark.service.mapper.ExcuseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Servicio para generar excusas usando IA (Ollama)
 * Nivel MEGALODON 🦈🦈🦈
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AIExcuseGeneratorService {

    private final ChatClient.Builder chatClientBuilder;
    private final ExcuseRepository excuseRepository;
    private final ExcuseGeneratorService fallbackService;
    private final MemeService memeService;
    private final LawService lawService;

    private static final String EXCUSE_GENERATION_PROMPT = """
            Sos un desarrollador argentino experto en generar excusas técnicas creativas y realistas.
            
            Generá una excusa tech siguiendo esta estructura JSON:
            {
              contexto: Descripción del escenario problemático,
              causa: La razón técnica del problema,
              consecuencia: El impacto del problema,
              recomendacion: Solución técnica profesional
            }
            
            Contexto adicional: {context}
            
            IMPORTANTE:
            - Usá lenguaje técnico profesional (deployment, pipeline, microservicios, etc)
            - Las excusas deben ser coherentes y realistas
            - Evitá humor excesivo en el JSON principal
            - Respondé SOLO con el JSON válido, sin texto adicional
            - Usá comillas dobles en los campos JSON
            """;

    /**
     * Genera excusa usando IA con contexto opcional
     */
    public ExcuseResponseDTO generateAIExcuse(String userContext) {
        try {
            log.info("Generando excusa con IA. Contexto: {}", userContext);
            
            ChatClient chatClient = chatClientBuilder.build();
            
            String context = (userContext != null && !userContext.isBlank()) 
                ? userContext 
                : "Situación general de desarrollo de software";
            
            PromptTemplate promptTemplate = new PromptTemplate(EXCUSE_GENERATION_PROMPT);
            Prompt prompt = promptTemplate.create(Map.of("context", context));
            
            String response = chatClient.prompt(prompt)
                    .call()
                    .content();
            
            log.debug("Respuesta de IA: {}", response);
            
            // Parsear la respuesta JSON
            Excuse excuse = parseAIResponse(response);
            
            if (excuse != null) {
                Excuse saved = excuseRepository.save(excuse);
                log.info("Excusa generada con IA guardada con ID: {}", saved.getId());
                return ExcuseMapper.toResponse(saved);
            }
            
            log.warn("No se pudo parsear la respuesta de IA, usando fallback");
            return fallbackService.generateRandomExcuse();
            
        } catch (Exception e) {
            log.error("Error generando excusa con IA: {}", e.getMessage());
            log.info("Usando generación clásica como fallback");
            return fallbackService.generateRandomExcuse();
        }
    }

    /**
     * Genera excusa ULTRA con IA (excusa + meme + ley)
     */
    public ExcuseResponseDTO generateAIUltraExcuse(String userContext) {
        try {
            ExcuseResponseDTO excuse = generateAIExcuse(userContext);
            
            if (excuse != null) {
                return enrichExcuseWithMemeAndLaw(excuse.getId());
            }
            
            return excuse;
            
        } catch (Exception e) {
            log.error("Error generando excusa ULTRA con IA: {}", e.getMessage());
            return fallbackService.generateUltraSharkExcuse().getExcusa();
        }
    }

    /**
     * Parsea la respuesta JSON de la IA usando AIResponseParser
     */
    private Excuse parseAIResponse(String response) {
        try {
            // Limpiar markdown usando utilidad
            String cleanResponse = AIResponseParser.cleanMarkdownWrapper(response);
            
            // Extraer JSON si hay texto adicional
            cleanResponse = AIResponseParser.extractJsonFromText(cleanResponse);
            if (cleanResponse == null) {
                log.warn("No se encontró JSON válido en respuesta");
                return null;
            }
            
            // Parsear campos usando AIResponseParser (evita duplicación)
            String contexto = AIResponseParser.extractJsonField(cleanResponse, "contexto");
            String causa = AIResponseParser.extractJsonField(cleanResponse, "causa");
            String consecuencia = AIResponseParser.extractJsonField(cleanResponse, "consecuencia");
            String recomendacion = AIResponseParser.extractJsonField(cleanResponse, "recomendacion");
            
            if (contexto != null && causa != null && consecuencia != null && recomendacion != null) {
                return Excuse.builder()
                        .contexto(contexto)
                        .causa(causa)
                        .consecuencia(consecuencia)
                        .recomendacion(recomendacion)
                        .build();
            }
            
            return null;
            
        } catch (Exception e) {
            log.error("Error parseando respuesta de IA: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Enriquece una excusa existente con meme y ley aleatorios
     * Método extraído para evitar duplicación de código
     */
    private ExcuseResponseDTO enrichExcuseWithMemeAndLaw(Long excuseId) {
        var meme = memeService.getRandomMeme();
        var law = lawService.getRandomLaw();
        
        Excuse excuseEntity = excuseRepository.findById(excuseId).orElse(null);
        if (excuseEntity != null) {
            if (meme != null) {
                excuseEntity.setMeme(meme.getText());
            }
            if (law != null) {
                excuseEntity.setLey(law.getType() + " - " + law.getContent());
            }
            Excuse saved = excuseRepository.save(excuseEntity);
            return ExcuseMapper.toResponse(saved);
        }
        
        return null;
    }
}
