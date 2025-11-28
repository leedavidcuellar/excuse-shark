package com.excusasshark.service;

import lombok.extern.slf4j.Slf4j;

/**
 * Utilidad para parsear respuestas JSON de Ollama/LLMs
 * Extraído para facilitar testing unitario
 */
@Slf4j
public class AIResponseParser {

    /**
     * Limpia respuesta de IA eliminando wrappers de markdown
     * Ollama a veces envuelve JSON en ```json ... ```
     */
    public static String cleanMarkdownWrapper(String response) {
        if (response == null || response.isBlank()) {
            return response;
        }

        String cleaned = response.trim();
        
        // Remover ```json al inicio
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7).trim();
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3).trim();
        }
        
        // Remover ``` al final
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3).trim();
        }
        
        return cleaned;
    }

    /**
     * Extrae JSON de texto que puede contener prefijos/sufijos
     * Busca primer { y último }
     */
    public static String extractJsonFromText(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        int startIdx = text.indexOf('{');
        int endIdx = text.lastIndexOf('}');
        
        if (startIdx == -1 || endIdx == -1 || startIdx >= endIdx) {
            return null;
        }
        
        return text.substring(startIdx, endIdx + 1);
    }

    /**
     * Extrae valor de un campo JSON simple
     * Maneja formato: "campo": "valor"
     */
    public static String extractJsonField(String json, String fieldName) {
        if (json == null || fieldName == null) {
            return null;
        }

        String searchPattern = "\"" + fieldName + "\"";
        int fieldIdx = json.indexOf(searchPattern);
        
        if (fieldIdx == -1) {
            return null;
        }

        // Buscar el inicio del valor (después de :)
        int colonIdx = json.indexOf(':', fieldIdx);
        if (colonIdx == -1) {
            return null;
        }

        // Saltar espacios y comillas
        int valueStart = colonIdx + 1;
        while (valueStart < json.length() && 
               (json.charAt(valueStart) == ' ' || json.charAt(valueStart) == '"')) {
            valueStart++;
        }

        // Buscar el final del valor (antes de " o ,)
        int valueEnd = valueStart;
        while (valueEnd < json.length() && 
               json.charAt(valueEnd) != '"' && 
               json.charAt(valueEnd) != ',') {
            valueEnd++;
        }

        if (valueStart >= valueEnd) {
            return null;
        }

        return json.substring(valueStart, valueEnd).trim();
    }

    /**
     * Valida que un JSON tenga todos los campos requeridos no vacíos
     */
    public static boolean hasAllRequiredFields(String json, String... requiredFields) {
        if (json == null || requiredFields == null) {
            return false;
        }

        for (String field : requiredFields) {
            String value = extractJsonField(json, field);
            if (value == null || value.isBlank()) {
                return false;
            }
        }

        return true;
    }
}
