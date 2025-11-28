# 🦈🦈🦈 NIVEL MEGALODON - Generación con IA

## Generación de Excusas usando Inteligencia Artificial

El nivel **Megalodon** integra **Spring AI + Ollama** para generar excusas técnicas usando modelos de lenguaje (LLM).

---

## 🎯 Características

- ✅ **Integración con Ollama**: Soporta múltiples modelos (OpenAI, Claude, Gemini, Llama, Mistral, etc)
- ✅ **Fallback Automático**: Si falla la IA, usa generación clásica
- ✅ **Contexto Personalizado**: Genera excusas específicas según contexto del usuario
- ✅ **Prompts Optimizados**: Templates diseñados para mantener estilo tech argentino
- ✅ **Parsing Robusto**: Extracción inteligente del JSON de respuesta
- ✅ **Modo ULTRA**: Combina IA + memes + leyes

---

## 📦 Requisitos

### 1. Instalar Ollama

**Windows/Mac/Linux:**
```bash
# Descargar desde: https://ollama.com/download

# Verificar instalación
ollama --version
```

### 2. Descargar Modelo

```bash
# Modelos recomendados:
ollama pull llama3.2      # Rápido, creativo (3.2B parámetros)
ollama pull mistral       # Excelente para texto (7B parámetros)
ollama pull codellama     # Especializado en código (7B parámetros)
ollama pull llama3        # Más potente (8B parámetros)

# Ver modelos instalados
ollama list
```

### 3. Ejecutar Ollama

```bash
# Iniciar servidor (localhost:11434)
ollama serve
```

---

## ⚙️ Configuración

### application.properties

```properties
# URL de Ollama
spring.ai.ollama.base-url=http://localhost:11434

# Modelo a usar (cambiar según disponibilidad)
spring.ai.ollama.chat.options.model=llama3.2

# Temperatura (0.0 = determinista, 1.0 = muy creativo)
spring.ai.ollama.chat.options.temperature=0.7

# Máximo tokens en respuesta
spring.ai.ollama.chat.options.max-tokens=1000

# Top P (diversidad de respuestas)
spring.ai.ollama.chat.options.top-p=0.9
```

### Cambiar Modelo en Runtime

Modificar `spring.ai.ollama.chat.options.model` según el modelo descargado:
- `llama3.2` - Rápido, ideal para desarrollo
- `mistral` - Mejor calidad de texto
- `codellama` - Enfocado en código y tech
- `llama3` - Más completo pero más lento

---

## 🔌 Endpoints Megalodon

### 1. Excusa con IA

```bash
GET /api/excuses/ai

# Ejemplos:
curl "http://localhost:8080/api/excuses/ai"
curl "http://localhost:8080/api/excuses/ai?context=microservicios"
curl "http://localhost:8080/api/excuses/ai?context=deploy de viernes"
```

**Respuesta:**
```json
{
  "id": 10,
  "contexto": "El microservicio de autenticación manejaba 50K requests/segundo",
  "causa": "No se implementó rate limiting ni circuit breakers",
  "consecuencia": "Los costos de AWS subieron 800% en 3 horas",
  "recomendacion": "Implementar Resilience4j con límites por tenant",
  "meme": null,
  "ley": null,
  "roleTarget": null,
  "createdAt": "2024-01-15T10:40:00"
}
```

### 2. Excusa ULTRA con IA

```bash
GET /api/excuses/ai/ultra

# Ejemplo:
curl "http://localhost:8080/api/excuses/ai/ultra?context=deploy de producción"
```

**Respuesta:**
```json
{
  "id": 11,
  "contexto": "Deploy en viernes a las 17hs sin testing",
  "causa": "PM insistió que eran solo 3 líneas de código",
  "consecuencia": "Sistema caído 4 horas, 100K usuarios afectados",
  "recomendacion": "Freeze window los viernes + rollback automatizado",
  "meme": "Mirá vos... commiteaste la contraseña en el repo público",
  "ley": "MURPHY - Si algo puede salir mal, saldrá mal en el peor momento",
  "roleTarget": null,
  "createdAt": "2024-01-15T10:45:00"
}
```

---

## 🧠 Prompt Engineering

### Template Usado

```
Sos un desarrollador argentino experto en generar excusas técnicas creativas y realistas.

Generá una excusa tech siguiendo esta estructura JSON:
{
  "contexto": "Descripción del escenario problemático",
  "causa": "La razón técnica del problema",
  "consecuencia": "El impacto del problema",
  "recomendacion": "Solución técnica profesional"
}

Contexto adicional: {context}

IMPORTANTE:
- Usá lenguaje técnico profesional (deployment, pipeline, microservicios, etc)
- Las excusas deben ser coherentes y realistas
- Evitá humor excesivo en el JSON principal
- Respondé SOLO con el JSON, sin texto adicional
```

### Personalización

Para modificar el estilo de generación, editar:
```java
// AIExcuseGeneratorService.java
private static final String EXCUSE_GENERATION_PROMPT = """
    // Tu prompt personalizado aquí
    """;
```

---

## 🛡️ Fallback Automático

Si Ollama no está disponible o falla la generación:

```java
try {
    // Intenta generar con IA
    return aiExcuseGeneratorService.generateAIExcuse(context);
} catch (Exception e) {
    // Fallback automático a generación clásica
    log.info("Usando generación clásica como fallback");
    return fallbackService.generateRandomExcuse();
}
```

**Ventajas:**
- ✅ La API nunca falla
- ✅ Funciona sin Ollama (modo degradado)
- ✅ Logs claros de fallback
- ✅ Tests no requieren Ollama

---

## 🧪 Testing

### Test Manual

```bash
# 1. Verificar Ollama activo
curl http://localhost:11434/api/tags

# 2. Generar excusa con IA
curl "http://localhost:8080/api/excuses/ai?context=kubernetes crash"

# 3. Generar ULTRA con IA
curl "http://localhost:8080/api/excuses/ai/ultra?context=incident de producción"
```

### Test con PowerShell

```powershell
# Test básico
$ai = Invoke-RestMethod "http://localhost:8080/api/excuses/ai"
$ai | Format-List

# Test con contexto
$ai = Invoke-RestMethod "http://localhost:8080/api/excuses/ai?context=docker container crash"
Write-Host "CONTEXTO:" -ForegroundColor Cyan
Write-Host $ai.contexto
Write-Host "`nCAUSA:" -ForegroundColor Magenta  
Write-Host $ai.causa
Write-Host "`nCONSECUENCIA:" -ForegroundColor Red
Write-Host $ai.consecuencia
Write-Host "`nRECOMENDACIÓN:" -ForegroundColor Green
Write-Host $ai.recomendacion
```

---

## 📊 Comparación Generación Clásica vs IA

| Característica | Clásica | IA (Megalodon) |
|----------------|---------|----------------|
| **Velocidad** | ⚡ Instantánea | 🕐 1-3 segundos |
| **Creatividad** | 🎲 Aleatoria | 🧠 Contextual |
| **Coherencia** | ✅ Garantizada | ✅ Alta (depende del modelo) |
| **Personalización** | ❌ Limitada | ✅ Por contexto |
| **Requisitos** | ✅ Ninguno | 📦 Ollama + modelo |
| **Costo** | 💰 Gratis | 💰 Gratis (local) |
| **Fallback** | - | ✅ A clásica |

---

## 🚀 Roadmap Megalodon

### Implementado ✅
- [x] Integración Spring AI + Ollama
- [x] Endpoint `/api/excuses/ai`
- [x] Endpoint `/api/excuses/ai/ultra`
- [x] Fallback a generación clásica
- [x] Configuración flexible de modelos
- [x] Parsing robusto de JSON
- [x] Logging detallado

### Próximas Mejoras 🔜
- [ ] Cache de respuestas IA (Redis)
- [ ] Rate limiting por usuario
- [ ] Métricas de calidad de respuestas
- [ ] A/B testing IA vs Clásica
- [ ] Fine-tuning de prompts
- [ ] Soporte para streaming de respuestas
- [ ] Tests unitarios con mocks

---

## 🐛 Troubleshooting

### Ollama no responde

```bash
# Verificar que Ollama esté corriendo
curl http://localhost:11434/api/tags

# Si no responde, reiniciar Ollama
ollama serve
```

### Modelo no encontrado

```bash
# Listar modelos instalados
ollama list

# Descargar el modelo configurado
ollama pull llama3.2
```

### Respuestas lentas

```properties
# Reducir max-tokens
spring.ai.ollama.chat.options.max-tokens=500

# Usar modelo más rápido
spring.ai.ollama.chat.options.model=llama3.2
```

### Excusas poco coherentes

```properties
# Reducir temperatura (más determinista)
spring.ai.ollama.chat.options.temperature=0.3

# O probar otro modelo
spring.ai.ollama.chat.options.model=mistral
```

---

## 📚 Referencias

- **Spring AI**: https://docs.spring.io/spring-ai/reference/
- **Ollama**: https://ollama.com/
- **Modelos disponibles**: https://ollama.com/library
- **Llama 3.2**: https://ollama.com/library/llama3.2
- **Mistral**: https://ollama.com/library/mistral

---

**🦈🦈🦈 Nivel Megalodon Completo - IA + Excusas Tech = 🔥**
