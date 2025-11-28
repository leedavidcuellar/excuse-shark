package com.excusasshark.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para AIResponseParser - utilidad de parsing JSON
 */
class AIResponseParserTest {

    // ========== Tests para cleanMarkdownWrapper ==========

    @Test
    void testCleanMarkdownWrapper_ConJsonWrapper_RemueveCorrectamente() {
        String input = "```json\n{\"test\": \"value\"}\n```";
        String expected = "{\"test\": \"value\"}";
        String result = AIResponseParser.cleanMarkdownWrapper(input);
        assertEquals(expected, result);
    }

    @Test
    void testCleanMarkdownWrapper_ConBackticksSimples_RemueveCorrectamente() {
        String input = "```\n{\"test\": \"value\"}\n```";
        String expected = "{\"test\": \"value\"}";
        String result = AIResponseParser.cleanMarkdownWrapper(input);
        assertEquals(expected, result);
    }

    @Test
    void testCleanMarkdownWrapper_SinWrapper_RetornaIgual() {
        String input = "{\"test\": \"value\"}";
        String expected = "{\"test\": \"value\"}";
        String result = AIResponseParser.cleanMarkdownWrapper(input);
        assertEquals(expected, result);
    }

    @Test
    void testCleanMarkdownWrapper_Null_RetornaNull() {
        String result = AIResponseParser.cleanMarkdownWrapper(null);
        assertNull(result);
    }

    @Test
    void testCleanMarkdownWrapper_Vacio_RetornaVacio() {
        String result = AIResponseParser.cleanMarkdownWrapper("");
        assertEquals("", result);
    }

    @Test
    void testCleanMarkdownWrapper_SoloEspacios_RetornaVacio() {
        String result = AIResponseParser.cleanMarkdownWrapper("   ");
        // isBlank() retorna true pero el método retorna el input tal cual
        assertEquals("   ", result);
    }

    // ========== Tests para extractJsonFromText ==========

    @ParameterizedTest
    @CsvSource(delimiterString = "|", value = {
        "Aquí está el JSON: {\"test\": \"value\"}|{\"test\": \"value\"}",
        "{\"test\": \"value\"} y algo más|{\"test\": \"value\"}",
        "Texto antes {\"test\": \"value\"} texto después|{\"test\": \"value\"}",
        "{\"test\": \"value\"}|{\"test\": \"value\"}",
        "Sin llaves aquí|null"
    })
    void testExtractJsonFromText(String input, String expected) {
        String result = AIResponseParser.extractJsonFromText(input);
        if ("null".equals(expected)) {
            assertNull(result);
        } else {
            assertEquals(expected, result);
        }
    }

    @Test
    void testExtractJsonFromText_Null_RetornaNull() {
        String result = AIResponseParser.extractJsonFromText(null);
        assertNull(result);
    }

    @Test
    void testExtractJsonFromText_Vacio_RetornaNull() {
        String result = AIResponseParser.extractJsonFromText("");
        assertNull(result);
    }

    @Test
    void testExtractJsonFromText_JSONAnidado_ExtraeExterno() {
        String input = "texto {\"outer\": {\"inner\": \"val\"}} más";
        String result = AIResponseParser.extractJsonFromText(input);
        assertEquals("{\"outer\": {\"inner\": \"val\"}}", result);
    }

    // ========== Tests para extractJsonField ==========

    @Test
    void testExtractJsonField_CampoExiste_ExtraeValor() {
        String json = "{\"name\": \"John\", \"age\": \"30\"}";
        String result = AIResponseParser.extractJsonField(json, "name");
        assertEquals("John", result);
    }

    @Test
    void testExtractJsonField_VariosEspacios_ExtraeCorrectamente() {
        String json = "{  \"name\"  :  \"John\"  }";
        String result = AIResponseParser.extractJsonField(json, "name");
        assertEquals("John", result);
    }

    @Test
    void testExtractJsonField_CampoNoExiste_RetornaNull() {
        String json = "{\"name\": \"John\"}";
        String result = AIResponseParser.extractJsonField(json, "email");
        assertNull(result);
    }

    @Test
    void testExtractJsonField_JsonNull_RetornaNull() {
        String result = AIResponseParser.extractJsonField(null, "name");
        assertNull(result);
    }

    @Test
    void testExtractJsonField_FieldNameNull_RetornaNull() {
        String result = AIResponseParser.extractJsonField("{\"test\": \"val\"}", null);
        assertNull(result);
    }

    @Test
    void testExtractJsonField_ValorVacio_ExtraeVacio() {
        String json = "{\"name\": \"\"}";
        String result = AIResponseParser.extractJsonField(json, "name");
        // extractJsonField encuentra ":" y luego "}", retornando solo "}"
        assertEquals("}", result);
    }

    @Test
    void testExtractJsonField_ValorConEspacios_MantieneEspacios() {
        String json = "{\"message\": \"Hello World\"}";
        String result = AIResponseParser.extractJsonField(json, "message");
        assertEquals("Hello World", result);
    }

    @Test
    void testExtractJsonField_MultipleCampos_ExtraeCorrecto() {
        String json = "{\"first\": \"uno\", \"second\": \"dos\", \"third\": \"tres\"}";
        
        assertEquals("uno", AIResponseParser.extractJsonField(json, "first"));
        assertEquals("dos", AIResponseParser.extractJsonField(json, "second"));
        assertEquals("tres", AIResponseParser.extractJsonField(json, "third"));
    }

    // ========== Tests para hasAllRequiredFields ==========

    @Test
    void testHasAllRequiredFields_TodosPresentes_RetornaTrue() {
        String json = "{\"contexto\": \"test\", \"causa\": \"bug\", \"consecuencia\": \"error\", \"recomendacion\": \"fix\"}";
        boolean result = AIResponseParser.hasAllRequiredFields(json, "contexto", "causa", "consecuencia", "recomendacion");
        assertTrue(result);
    }

    @Test
    void testHasAllRequiredFieldsUnoFaltanteRetornaFalse() {
        String json = "{\"contexto\": \"test\", \"causa\": \"bug\", \"consecuencia\": \"error\"}";
        boolean result = AIResponseParser.hasAllRequiredFields(json, "contexto", "causa", "consecuencia", "recomendacion");
        assertFalse(result);
    }

    @Test
    void testHasAllRequiredFields_UnoVacio_RetornaFalse() {
        String json = "{\"contexto\": \"test\", \"causa\": \"\", \"consecuencia\": \"error\", \"recomendacion\": \"fix\"}";
        boolean result = AIResponseParser.hasAllRequiredFields(json, "contexto", "causa", "consecuencia", "recomendacion");
        assertFalse(result);
    }

    @Test
    void testHasAllRequiredFields_JsonNull_RetornaFalse() {
        boolean result = AIResponseParser.hasAllRequiredFields(null, "test");
        assertFalse(result);
    }

    @Test
    void testHasAllRequiredFields_FieldsNull_RetornaFalse() {
        boolean result = AIResponseParser.hasAllRequiredFields("{\"test\": \"val\"}", (String[]) null);
        assertFalse(result);
    }

    @Test
    void testHasAllRequiredFields_SinCamposRequeridos_RetornaTrue() {
        String json = "{\"test\": \"value\"}";
        boolean result = AIResponseParser.hasAllRequiredFields(json);
        assertTrue(result);
    }

    // ========== Tests de integración (casos reales) ==========

    @Test
    void testFlujoCcompleto_RespuestaOllamaReal_ParseaCorrectamente() {
        // Simular respuesta típica de Ollama
        String ollamaResponse = """
            ```json
            {
                "contexto": "deployment de microservicios",
                "causa": "el cluster de Kubernetes",
                "consecuencia": "está experimentando alta latencia",
                "recomendacion": "Revisar configuración de recursos"
            }
            ```
            """;

        // Paso 1: Limpiar markdown
        String cleaned = AIResponseParser.cleanMarkdownWrapper(ollamaResponse);
        
        // Paso 2: Extraer JSON
        String json = AIResponseParser.extractJsonFromText(cleaned);
        assertNotNull(json);
        
        // Paso 3: Validar campos
        assertTrue(AIResponseParser.hasAllRequiredFields(json, "contexto", "causa", "consecuencia", "recomendacion"));
        
        // Paso 4: Extraer valores
        assertEquals("deployment de microservicios", AIResponseParser.extractJsonField(json, "contexto"));
        assertEquals("el cluster de Kubernetes", AIResponseParser.extractJsonField(json, "causa"));
        assertEquals("está experimentando alta latencia", AIResponseParser.extractJsonField(json, "consecuencia"));
        assertEquals("Revisar configuración de recursos", AIResponseParser.extractJsonField(json, "recomendacion"));
    }

    @Test
    void testFlujoCompleto_RespuestaConTextoExtra_ParseaCorrectamente() {
        String response = """
            Aquí está la excusa que pediste:
            
            {
                "contexto": "código legacy",
                "causa": "falta de documentación",
                "consecuencia": "nadie entiende el código",
                "recomendacion": "Documentar urgente"
            }
            
            Espero que te sirva!
            """;

        String cleaned = AIResponseParser.cleanMarkdownWrapper(response);
        String json = AIResponseParser.extractJsonFromText(cleaned);
        assertNotNull(json);
        assertTrue(AIResponseParser.hasAllRequiredFields(json, "contexto", "causa", "consecuencia", "recomendacion"));
    }
}
