# 🧪 Manual de Testing con Ollama

## Prerequisitos
```powershell
# 1. Iniciar Ollama
Start-Process -FilePath "C:\Users\lee.cuellar\AppData\Local\Programs\Ollama\ollama.exe" -ArgumentList "serve" -WindowStyle Hidden

# 2. Verificar Ollama
curl http://localhost:11434  # Debe responder: "Ollama is running"

# 3. Iniciar aplicación
java -jar target\excusas-shark-1.0.0.jar
```

## Tests Manuales

### Test 1: Endpoint AI Simple
```powershell
curl "http://localhost:8080/api/excuses/ai?context=deployment"
```

**Validaciones**:
- ✅ Responde JSON con 200 OK
- ✅ Contiene campos: `contexto`, `causa`, `consecuencia`, `recomendacion`
- ✅ Los textos son coherentes y relacionados con "deployment"
- ✅ Logs muestran: "Generando excusa con IA. Contexto: deployment"
- ❌ Si dice "Usando generación clásica como fallback" → Ollama no respondió

**Ejemplo de respuesta esperada**:
```json
{
  "id": 1,
  "contexto": "El deploy de producción se realizó sin validación previa",
  "causa": "El pipeline de CI/CD no ejecutó los tests de integración",
  "consecuencia": "Los usuarios experimentaron errores 500 durante 2 horas",
  "recomendacion": "Implementar gates de calidad en el pipeline con tests obligatorios",
  "meme": null,
  "ley": null,
  "roleTarget": null,
  "createdAt": "2025-11-27T19:15:30"
}
```

---

### Test 2: Endpoint AI Ultra (con Meme + Ley)
```powershell
curl "http://localhost:8080/api/excuses/ai/ultra?context=microservicios"
```

**Validaciones**:
- ✅ Responde JSON con 200 OK
- ✅ Contiene campos: `contexto`, `causa`, `consecuencia`, `recomendacion`
- ✅ **ADEMÁS** contiene: `meme` (texto gracioso), `ley` (nombre + enunciado)
- ✅ El contexto está relacionado con "microservicios"
- ✅ Logs muestran: "Generando excusa con IA. Contexto: microservicios"

**Ejemplo de respuesta esperada**:
```json
{
  "id": 2,
  "contexto": "La arquitectura de microservicios tiene latencia alta",
  "causa": "Falta de circuit breakers entre servicios",
  "consecuencia": "Timeouts en cascada cuando un servicio falla",
  "recomendacion": "Implementar Resilience4j con circuit breakers y fallbacks",
  "meme": "A mí no me gusta trabajar, a mí me gusta cobrar - Ricardo Fort",
  "ley": "MURPHY - Si algo puede salir mal, saldrá mal",
  "roleTarget": null,
  "createdAt": "2025-11-27T19:16:45"
}
```

---

### Test 3: Diferentes Contextos
Probá con distintos contextos para ver cómo se adapta la IA:

```powershell
# Testing
curl "http://localhost:8080/api/excuses/ai?context=testing"

# Base de datos
curl "http://localhost:8080/api/excuses/ai?context=database"

# Performance
curl "http://localhost:8080/api/excuses/ai?context=performance"

# CI/CD
curl "http://localhost:8080/api/excuses/ai?context=ci-cd"

# Código Legacy
curl "http://localhost:8080/api/excuses/ai?context=codigo-legado"
```

**Validación**:
- ✅ Cada respuesta debe estar relacionada con el contexto enviado
- ✅ Los 4 campos deben formar una narrativa coherente
- ✅ No debe repetir las mismas excusas

---

### Test 4: Contexto Vacío (Fallback a default)
```powershell
curl "http://localhost:8080/api/excuses/ai"
```

**Validación**:
- ✅ Responde igual, pero usa contexto genérico
- ✅ Logs muestran: "Situación general de desarrollo de software"

---

### Test 5: Comparación AI vs Classic
Probá el endpoint clásico para comparar:

```powershell
curl "http://localhost:8080/api/excuses?context=CODIGO_LEGADO"
```

**Diferencias esperadas**:
- **Classic**: Fragmentos aleatorios de la BD (pueden ser incoherentes entre sí)
- **AI**: Narrativa coherente generada por Ollama adaptada al contexto

---

### Test 6: Verificar Logs
Revisá los logs en tiempo real mientras hacés los requests:

```powershell
# Buscar líneas importantes
Select-String -Path ".\logs\spring.log" -Pattern "Generando excusa con IA"
```

**Logs esperados SI Ollama funciona**:
```
INFO : Generando excusa con IA. Contexto: deployment
DEBUG: Respuesta de IA: {"contexto":"...","causa":"..."}
INFO : Excusa generada con IA guardada con ID: 1
```

**Logs esperados SI Ollama falla**:
```
INFO : Generando excusa con IA. Contexto: deployment
ERROR: Error generando excusa con IA: Connect timed out
INFO : Usando generación clásica como fallback
```

---

### Test 7: Swagger UI (Interfaz Visual)
Abrí en el navegador:
```
http://localhost:8080/swagger-ui.html
```

1. Expandí **ExcuseController**
2. Probá `/api/excuses/ai` y `/api/excuses/ai/ultra`
3. Ingresá diferentes contextos en "Try it out"
4. Observá las respuestas JSON formateadas

---

### Test 8: Stress Test (Ollama bajo carga)
```powershell
# Hacer 10 requests rápidos
1..10 | ForEach-Object {
    Write-Host "Request $_"
    curl "http://localhost:8080/api/excuses/ai?context=test$_" | ConvertFrom-Json | Select-Object id, contexto
    Start-Sleep -Seconds 1
}
```

**Validaciones**:
- ✅ Todas las respuestas exitosas (200 OK)
- ✅ Los IDs son únicos y secuenciales
- ✅ Ollama responde consistentemente
- ⚠️ Si alguno falla → Verifica logs para ver si hubo timeout

---

### Test 9: Fallback Manual (Detener Ollama)
```powershell
# 1. Detener Ollama
Get-Process -Name ollama -ErrorAction SilentlyContinue | Stop-Process -Force

# 2. Probar endpoint
curl "http://localhost:8080/api/excuses/ai?context=test"
```

**Validación**:
- ✅ Debe responder 200 OK (NO error 500)
- ✅ Logs muestran: "Error generando excusa con IA" + "Usando generación clásica como fallback"
- ✅ La excusa es válida pero construida con fragmentos random

---

### Test 10: Verificar Base de Datos H2
Abrí en el navegador:
```
http://localhost:8080/h2-console
```

**Configuración**:
- JDBC URL: `jdbc:h2:mem:excusasdb`
- User: `sa`
- Password: _(vacío)_

**Query para verificar excusas guardadas**:
```sql
SELECT * FROM EXCUSES ORDER BY CREATED_AT DESC;
```

**Validaciones**:
- ✅ Cada request a `/api/excuses/ai` crea un registro
- ✅ Los campos `contexto`, `causa`, `consecuencia`, `recomendacion` están completos
- ✅ Los registros Ultra tienen `meme` y `ley` también

---

## Checklist de Validación Completa

### ✅ Funcionalidad
- [ ] Ollama responde en http://localhost:11434
- [ ] App inicia sin errores en puerto 8080
- [ ] Endpoint `/api/excuses/ai` responde 200 OK
- [ ] Endpoint `/api/excuses/ai/ultra` incluye meme + ley
- [ ] Las excusas AI son coherentes con el contexto enviado
- [ ] Las excusas AI no son fragmentos aleatorios (narrativa completa)

### ✅ Resiliencia
- [ ] Si Ollama no responde → Fallback funciona (no 500 error)
- [ ] Logs claros indicando uso de fallback
- [ ] La app no se cae si Ollama muere

### ✅ Integración
- [ ] Los JSONs tienen todos los 4 campos requeridos
- [ ] Swagger UI funciona correctamente
- [ ] H2 Console muestra registros guardados
- [ ] Diferentes contextos generan respuestas diferentes

### ✅ Coverage
- [ ] 206/206 tests pasando
- [ ] 89% coverage (reportado por JaCoCo)
- [ ] AIResponseParser 100% cubierto

---

## Troubleshooting

### Problema: "Error generando excusa con IA: The template string is not valid"
**Solución**: Template tiene comillas incorrectas. Ya arreglado en la versión actual.

### Problema: "Ollama is not running"
**Solución**:
```powershell
Start-Process -FilePath "C:\Users\lee.cuellar\AppData\Local\Programs\Ollama\ollama.exe" -ArgumentList "serve" -WindowStyle Hidden
```

### Problema: "This site can't be reached" (puerto 8080)
**Solución**:
```powershell
java -jar target\excusas-shark-1.0.0.jar
```

### Problema: Respuestas muy lentas (>30s)
**Causas posibles**:
- Ollama procesando modelo pesado → Normal en primera ejecución
- CPU/RAM limitada → Configurar timeout más bajo
- Modelo llama3.2 necesita warmup → Primera request siempre es lenta

---

## Resultado Esperado

Si TODO funciona correctamente, deberías ver:

**En los logs**:
```
INFO : Started ExcusasSharkApplication in 8.888 seconds
INFO : Generando excusa con IA. Contexto: deployment
DEBUG: Respuesta de IA: {"contexto":"El deploy..."}
INFO : Excusa generada con IA guardada con ID: 1
```

**En la respuesta**:
```json
{
  "id": 1,
  "contexto": "El deploy de producción...",
  "causa": "Pipeline de CI/CD mal configurado...",
  "consecuencia": "Downtime de 2 horas...",
  "recomendacion": "Implementar smoke tests automáticos...",
  "meme": null,
  "ley": null,
  "roleTarget": null,
  "createdAt": "2025-11-27T19:20:00"
}
```

**Diferencia clave vs fallback**:
- **Con Ollama**: Narrativa coherente, contexto respetado
- **Con Fallback**: Fragmentos random que pueden no tener sentido juntos
