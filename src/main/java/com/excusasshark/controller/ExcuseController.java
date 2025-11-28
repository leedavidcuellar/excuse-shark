package com.excusasshark.controller;

import com.excusasshark.dto.ExcuseResponseDTO;
import com.excusasshark.dto.UltraSharkExcuseDTO;
import com.excusasshark.model.RoleType;
import com.excusasshark.service.AIExcuseGeneratorService;
import com.excusasshark.service.ExcuseGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para generación de excusas tech
 * Endpoint raíz: /api/excuses
 */
@RestController
@RequestMapping("/api/excuses")
@RequiredArgsConstructor
@Tag(name = "Excuses", description = "Generación de excusas técnicas")
public class ExcuseController {

    private final ExcuseGeneratorService excuseGeneratorService;
    private final AIExcuseGeneratorService aiExcuseGeneratorService;

    @GetMapping("/random")
    @Operation(summary = "Generar una excusa aleatoria",
            description = "Compone una excusa con 4 fragmentos aleatorios")
    @ApiResponse(responseCode = "200", description = "Excusa generada exitosamente")
    public ResponseEntity<ExcuseResponseDTO> generateRandomExcuse() {
        ExcuseResponseDTO excuse = excuseGeneratorService.generateRandomExcuse();
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/daily")
    @Operation(summary = "Obtener la excusa del día",
            description = "Retorna la misma excusa una vez por día")
    @ApiResponse(responseCode = "200", description = "Excusa del día")
    public ResponseEntity<ExcuseResponseDTO> getDailyExcuse() {
        ExcuseResponseDTO excuse = excuseGeneratorService.getDailyExcuse();
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/meme")
    @Operation(summary = "Generar excusa con meme",
            description = "Compone excusa + meme argentino tech random")
    @ApiResponse(responseCode = "200", description = "Excusa con meme")
    public ResponseEntity<ExcuseResponseDTO> generateExcuseWithMeme() {
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseWithMeme();
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/law")
    @Operation(summary = "Generar excusa con ley tech",
            description = "Compone excusa + ley técnica (Murphy, Hofstadter, etc)")
    @ApiResponse(responseCode = "200", description = "Excusa con ley")
    public ResponseEntity<ExcuseResponseDTO> generateExcuseWithLaw() {
        ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseWithLaw();
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/ultra")
    @Operation(summary = "Generar UltraShark: excusa + meme + ley",
            description = "Modo ULTRA: Excusa completa + meme + ley (lo máximo)")
    @ApiResponse(responseCode = "200", description = "UltraShark completo")
    public ResponseEntity<UltraSharkExcuseDTO> generateUltraSharkExcuse() {
        UltraSharkExcuseDTO excuse = excuseGeneratorService.generateUltraSharkExcuse();
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Generar excusa para rol específico",
            description = "Excusa dirigida a: DEV, QA, DEVOPS, PM, ARCHITECT, DBA")
    @ApiResponse(responseCode = "200", description = "Excusa para rol")
    @ApiResponse(responseCode = "400", description = "Rol inválido")
    public ResponseEntity<ExcuseResponseDTO> generateExcuseForRole(@PathVariable String role) {
        try {
            RoleType roleType = RoleType.valueOf(role.toUpperCase());
            ExcuseResponseDTO excuse = excuseGeneratorService.generateExcuseForRole(roleType);
            return ResponseEntity.ok(excuse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las excusas generadas",
            description = "Lista de todas las excusas almacenadas")
    @ApiResponse(responseCode = "200", description = "Lista de excusas")
    public ResponseEntity<List<ExcuseResponseDTO>> getAllExcuses() {
        List<ExcuseResponseDTO> excuses = excuseGeneratorService.getAll();
        return ResponseEntity.ok(excuses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener excusa por ID",
            description = "Recupera una excusa específica por su ID")
    @ApiResponse(responseCode = "200", description = "Excusa encontrada")
    @ApiResponse(responseCode = "404", description = "Excusa no encontrada")
    public ResponseEntity<ExcuseResponseDTO> getExcuseById(@PathVariable Long id) {
        ExcuseResponseDTO excuse = excuseGeneratorService.getById(id);
        if (excuse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(excuse);
    }

    // ========== NIVEL MEGALODON: Generación con IA 🦈🦈🦈 ==========

    @GetMapping("/ai")
    @Operation(summary = "🦈🦈🦈 MEGALODON: Generar excusa con IA (Ollama)",
            description = "Usa modelos de lenguaje (LLM) para generar excusas creativas. Soporta OpenAI, Claude, Gemini, Llama vía Ollama. Fallback automático a generación clásica si falla.")
    @ApiResponse(responseCode = "200", description = "Excusa generada con IA")
    @ApiResponse(responseCode = "500", description = "Error en IA, usó fallback clásico")
    public ResponseEntity<ExcuseResponseDTO> generateAIExcuse(
            @RequestParam(required = false, defaultValue = "") String context) {
        ExcuseResponseDTO excuse = aiExcuseGeneratorService.generateAIExcuse(context);
        return ResponseEntity.ok(excuse);
    }

    @GetMapping("/ai/ultra")
    @Operation(summary = "🦈🦈🦈 MEGALODON ULTRA: Excusa IA + meme + ley",
            description = "Modo ULTRA con IA: Genera excusa con LLM + meme argentino + ley técnica. El máximo nivel de creatividad.")
    @ApiResponse(responseCode = "200", description = "Excusa ULTRA generada con IA")
    public ResponseEntity<ExcuseResponseDTO> generateAIUltraExcuse(
            @RequestParam(required = false, defaultValue = "") String context) {
        ExcuseResponseDTO excuse = aiExcuseGeneratorService.generateAIUltraExcuse(context);
        return ResponseEntity.ok(excuse);
    }
}
