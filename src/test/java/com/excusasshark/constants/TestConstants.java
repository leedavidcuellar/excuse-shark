package com.excusasshark.constants;

/**
 * Constantes compartidas para tests.
 * Centraliza valores de texto y mensajes reutilizables en toda la suite de tests.
 */
public final class TestConstants {

    private TestConstants() {
        // Clase de constantes - no instanciable
    }

    // ==================== Textos de datos de test ====================
    
    // Fragmentos
    public static final String TEST_TEXT = "Texto";
    public static final String TEST_TEXT_CAUSA = "Texto causa";
    public static final String TEST_SOURCE = "Fuente";
    public static final String TEST_CATEGORY = "Categoria";
    public static final String TEST_CATEGORY_SHORT = "Cat";
    public static final String TEST_TYPE_CONTEXTO = "contexto";
    public static final String TEST_TYPE_INVALID = "inexistente";
    public static final String TEST_CONTEXT_FRAGMENT = "Test context fragment";
    public static final String TEST_FRAGMENT = "Test fragment";
    
    // Memes y Laws
    public static final String TEXT_MEME = "meme-text";
    public static final String TEXT_LAW_CONTENT = "ley-content";
    public static final String TEST_MEME = "Test meme";
    public static final String TEST_LAW_CONTENT = "Si algo puede salir mal, saldrá mal";
    
    // Excuse
    public static final String TEST_EXCUSE_CONTEXTO = "Test contexto";
    public static final String TEST_EXCUSE_CAUSA = "Test causa";
    public static final String TEST_EXCUSE_CONSECUENCIA = "Test consecuencia";
    public static final String TEST_EXCUSE_RECOMENDACION = "Test recomendacion";
    
    // Valores básicos sin prefijo Test
    public static final String CONTEXTO = "Contexto";
    public static final String CAUSA = "Causa";
    public static final String CONSECUENCIA = "Consecuencia";
    public static final String RECOMENDACION = "Recomendacion";
    public static final String ULTRA = "Ultra";
    public static final String ULTRA_CAUSA = "Ultra causa";
    public static final String AUTHOR = "Author";
    public static final String UP = "UP";
    public static final String VERSION_1_0_0 = "1.0.0";
    public static final String STATUS = "status";
    public static final String SERVICE = "service";
    public static final String VERSION = "version";
    public static final String TIMESTAMP = "timestamp";
    public static final String RANDOM_FRAGMENT = "Random fragment";
    public static final String NEW_FRAGMENT = "New fragment";
    public static final String DEVELOPMENT = "development";
    public static final String FUENTE = "Fuente";
    public static final String FUENTE2 = "Fuente2";
    public static final String CAT = "Cat";
    public static final String CAT2 = "Cat2";
    
    // Tipos de fragmento en minúsculas
    public static final String TEXT_CAUSA = "causa";
    public static final String TEXT_CONTEXTO = "contexto";
    public static final String TEXT_CONSECUENCIA = "consecuencia";
    public static final String TEXT_RECOMENDACION = "recomendacion";
    
    // Valores comunes
    public static final String TEST_SOURCE_VALUE = "TEST";
    public static final String TEST_SOURCE_UNIT = "UNIT-TEST";
    public static final String TEST_CATEGORY_VALUE = "testing";
    public static final String TEST_AUTHOR = "Test Author";
    public static final String TEST_AUTHOR_MURPHY = "Edward A. Murphy Jr.";
    
    // Valores adicionales para tests específicos
    public static final String TEST_MEME_DIVERTIDO = "Meme divertido";
    public static final String TEST_TWITTER = "Twitter";
    public static final String TEST_HUMOR = "humor";
    public static final String TEST_SOLO_TEXTO = "Solo texto";
    public static final String TEST_LEY_MURPHY = "Ley de Murphy";
    public static final String TEST_LEY_SIN_TIPO = "Ley sin tipo";
    public static final String TEST_LEY_HOFSTADTER = "Ley de Hofstadter";
    public static final String TEST_CONTEXTO_TEST = "Contexto test";
    public static final String TEST_CAUSA_TEST = "Causa test";
    public static final String TEST_CONSECUENCIA_TEST = "Consecuencia test";
    public static final String TEST_RECOMENDACION_TEST = "Recomendacion test";
    public static final String TEST_MEME_TEXT = "Meme text";
    public static final String TEST_CAUSA_TEST_SHORT = "Causa test";
    public static final String TEST_NEW_FRAGMENT = "New fragment";
    public static final String TEST_MEME_ULTRA = "Meme ultra";
    public static final String TEST_LEY_ULTRA = "Ley ultra";
    public static final String TEST_EXCUSAS_SHARK_API = "Excusas Shark API";
    public static final String TEST_MEME_TEST = "Meme test";
    public static final String TEST_LEY_TEST = "Ley test";
    public static final String TEST_FRAGMENT_SIN_TYPE = "Fragment sin type";
    public static final String TEST_LAW_SIN_TYPE = "Law sin type";
    public static final String TEST_FRAGMENT_VALIDO = "Fragment válido";
    public static final String TEST_LAW_VALIDA = "Law válida";
    public static final String TEST_FRAGMENT_INVALIDO = "Fragment con tipo inválido";
    public static final String TEST_LAW_INVALIDA = "Law con tipo inválido";
    public static final String TEST_UNKNOWN = "Unknown";
    public static final String TEST_MURPHY_AUTHOR = "Murphy";
    public static final String TEST_TEXTO_CAUSA = "Texto causa";
    public static final String TEST_TEXTO_CONTEXTO = "Texto contexto";
    
    // ==================== Mensajes de aserción ====================
    
    // Mensajes de excusas
    public static final String MSG_EXCUSE_NOT_NULL = "La excusa no debe ser nula";
    public static final String MSG_EXCUSE_WITH_MEME_NOT_NULL = "La excusa con meme no debe ser nula";
    public static final String MSG_EXCUSE_WITH_LAW_NOT_NULL = "La excusa con ley no debe ser nula";
    public static final String MSG_ULTRASHARK_NOT_NULL = "UltraShark no debe ser nula";
    
    // Mensajes de componentes
    public static final String MSG_CONTEXT_PRESENT = "El contexto debe estar presente";
    public static final String MSG_CAUSE_PRESENT = "La causa debe estar presente";
    public static final String MSG_CONSEQUENCE_PRESENT = "La consecuencia debe estar presente";
    public static final String MSG_RECOMMENDATION_PRESENT = "La recomendación debe estar presente";
    public static final String MSG_MEME_PRESENT = "El meme debe estar presente";
    public static final String MSG_LAW_PRESENT = "La ley debe estar presente";
    public static final String MSG_EXCUSE_PRESENT = "La excusa debe estar presente";
}
