# 🦈 Excusas Shark API

![Java](https://img.shields.io/badge/Java-17-green) ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2.8-brightgreen) ![License](https://img.shields.io/badge/License-MIT-blue)

**API REST para generar excusas técnicas argentinas** - Del *Mojarrita* (simple) al *White Shark* (ULTRA con meme + ley)

> 🤖 Sistema inteligente que compone excusas tech divertidas, creativas y técnicamente sólidas usando arquitectura hexagonal.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Quick Start](#quick-start)
- [Arquitectura](#arquitectura)
- [Endpoints](#endpoints)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Desarrollo Local](#desarrollo-local)
- [Docker](#docker)
- [Patrones y Principios](#patrones-y-principios)

---

## ✨ Características

### Modos de Generación

| Nivel | Descripción | Incluye |
|-------|-------------|---------|
| **Mojarrita** | Excusa básica | 4 fragmentos (contexto, causa, consecuencia, recomendación) |
| **Delfín** | + Meme | Excusa + meme argentino tech |
| **Tiburón** | + Ley | Excusa + ley/axioma técnico (Murphy, Hofstadter, etc) |
| **White Shark** | ULTRA | Excusa + meme + ley (lo máximo 🔥) |
| **🦈🦈🦈 Megalodon** | **IA** | **Generación con LLM (OpenAI, Claude, Gemini, Llama) vía Ollama** |

### Características Principales

- ✅ **4 Tipos de Fragmentos**: Contexto, Causa, Consecuencia, Recomendación
- ✅ **Segmentación por Rol**: Excusas dirigidas a DEV, QA, DevOps, PM, Architect, DBA
- ✅ **Memes Argentinos**: Colección de memes tech
- ✅ **Leyes Técnicas**: Murphy, Hofstadter, Dilbert, DevOps, Developer
- ✅ **🦈🦈🦈 Generación con IA**: Spring AI + Ollama (OpenAI, Claude, Gemini, Llama, etc)
- ✅ **Excusa del Día**: Reproducible (misma excusa dentro del mismo día)
- ✅ **Generación Determinística**: Control con seed para tests reproducibles
- ✅ **API Totalmente Documentada**: Swagger/OpenAPI 2.3.0
- ✅ **100% Code Coverage**: JaCoCo para cobertura de tests
- ✅ **Arquitectura Hexagonal**: Dominio independiente, fácil de testear

---

## 🚀 Quick Start

### Prerrequisitos

- **Java 17+**
- **Maven 3.9+**
- **Docker** (opcional, para containerización)
- **🦈🦈🦈 Ollama** (opcional, para generación con IA - Megalodon)

### Instalar Ollama (Opcional - para nivel Megalodon)

```bash
# Windows/Mac/Linux - Descargar desde:
https://ollama.com/download

# Verificar instalación
ollama --version

# Descargar modelo (ej: llama3.2, mistral, codellama)
ollama pull llama3.2

# Ejecutar Ollama (levanta en localhost:11434)
ollama serve
```

**Modelos recomendados para excusas:**
- `llama3.2` - Rápido y creativo
- `mistral` - Excelente para texto
- `codellama` - Especializado en tech
- Configurar en `application.properties`: `spring.ai.ollama.chat.options.model`

### Compilar y Ejecutar

```bash
# Clonar repositorio
git clone https://github.com/accenture/excusas-shark.git
cd excusas-shark

# Compilar
mvn clean package

# Ejecutar aplicación
mvn spring-boot:run

# La API estará disponible en: http://localhost:8080
```

### URLs Principales

```
🌐 API REST:           http://localhost:8080/api
📚 Swagger UI:         http://localhost:8080/swagger-ui.html
📖 OpenAPI JSON:       http://localhost:8080/api-docs
❤️  Health Check:       http://localhost:8080/health
💾 H2 Console:         http://localhost:8080/h2-console
```

---

## 🏗️ Arquitectura

### Patrón Hexagonal (Ports & Adapters)

```
┌─────────────────────────────────────────────────────┐
│                                                       │
│  ┌─────────────────────────────────────────────┐   │
│  │        Controllers (REST Adapters)          │   │
│  │   /api/excuses  /api/fragments  /health     │   │
│  └────────────────────┬────────────────────────┘   │
│                       │                             │
│  ┌────────────────────▼────────────────────────┐   │
│  │  DTOs (Request/Response Contracts)          │   │
│  │  FragmentRequestDTO, ExcuseResponseDTO      │   │
│  └────────────────────┬────────────────────────┘   │
│                       │                             │
│  ┌────────────────────▼────────────────────────┐   │
│  │     Service Layer (Domain Logic)            │   │
│  │ ExcuseGeneratorService, FragmentService     │   │
│  └────────────────────┬────────────────────────┘   │
│                       │                             │
│  ┌────────────────────▼────────────────────────┐   │
│  │  Repositories (JPA Adapters)                │   │
│  │  FragmentRepository, ExcuseRepository       │   │
│  └────────────────────┬────────────────────────┘   │
│                       │                             │
│  ┌────────────────────▼────────────────────────┐   │
│  │         H2 In-Memory Database               │   │
│  │    fragments | memes | laws | excuses       │   │
│  └─────────────────────────────────────────────┘   │
│                                                       │
└─────────────────────────────────────────────────────┘
```

### Capas

1. **Controllers** (Presentación)
   - HTTP REST adapters
   - Validación con `@Valid`
   - ResponseEntity con códigos HTTP correctos

2. **DTOs** (Contratos API)
   - `*RequestDTO`: Validación con Jakarta Validation
   - `*ResponseDTO`: Estructura de respuesta

3. **Services** (Lógica de Dominio)
   - ExcuseGeneratorService: Orquestación de generación
   - FragmentService, MemeService, LawService: CRUD
   - Null-safe patterns

4. **Repositories** (Persistencia)
   - Spring Data JPA
   - Query derivados por nombre
   - H2 en memoria

---

## 📡 Endpoints

### Generar Excusas

#### GET /api/excuses/random
Genera una excusa aleatoria (Mojarrita)

```bash
curl -X GET http://localhost:8080/api/excuses/random
```

**Response 200:**
```json
{
  "id": 1,
  "contexto": "El deploy de producción se realizó sin testing",
  "causa": "El código pasó entre las grietas del code review",
  "consecuencia": "El sistema comenzó a procesar datos duplicados",
  "recomendacion": "Implementar testing automático en el pipeline CI/CD",
  "meme": null,
  "ley": null,
  "roleTarget": "DEV",
  "createdAt": "2024-01-15T10:30:45"
}
```

#### GET /api/excuses/daily
Retorna la misma excusa dentro del mismo día (Mojarrita reproducible)

```bash
curl -X GET http://localhost:8080/api/excuses/daily
```

#### GET /api/excuses/meme
Excusa + meme argentino random (Delfín 🐬)

```bash
curl -X GET http://localhost:8080/api/excuses/meme
```

**Response 200:**
```json
{
  "id": 2,
  "contexto": "...",
  "causa": "...",
  "consecuencia": "...",
  "recomendacion": "...",
  "meme": "Con vos no se puede, hermano. Escribiste un SELECT * en producción",
  "ley": null,
  "roleTarget": null,
  "createdAt": "2024-01-15T10:32:00"
}
```

#### GET /api/excuses/law
Excusa + ley técnica random (Tiburón 🦈)

```bash
curl -X GET http://localhost:8080/api/excuses/law
```

**Response 200:**
```json
{
  "id": 3,
  "contexto": "...",
  "causa": "...",
  "consecuencia": "...",
  "recomendacion": "...",
  "meme": null,
  "ley": "Si algo puede salir mal, saldrá mal en el peor momento posible",
  "roleTarget": null,
  "createdAt": "2024-01-15T10:33:15"
}
```

#### GET /api/excuses/ultra
ULTRA: Excusa + meme + ley (White Shark 🦈🦈)

```bash
curl -X GET http://localhost:8080/api/excuses/ultra
```

**Response 200:**
```json
{
  "excusa": {
    "id": 4,
    "contexto": "...",
    "causa": "...",
    "consecuencia": "...",
    "recomendacion": "...",
    "meme": "Mirá vos... commiteaste la contraseña de admin en el repo público",
    "ley": "Siempre es más lento de lo que esperas, aún si descontas esta ley",
    "roleTarget": "QA",
    "createdAt": "2024-01-15T10:35:00"
  },
  "meme": "Mirá vos... commiteaste la contraseña de admin en el repo público",
  "ley": "Siempre es más lento de lo que esperas, aún si descontas esta ley"
}
```

---

### 🦈🦈🦈 MEGALODON - Generación con IA

**Nivel Megalodon usa Spring AI + Ollama** para generar excusas 100% coherentes y creativas adaptadas al contexto.

**Características:**
- ✅ Generación con LLM (Llama, Mistral, CodeLlama, etc)
- ✅ Narrativa coherente (no fragmentos aleatorios)
- ✅ Adaptación dinámica al contexto del usuario
- ✅ Fallback automático a generación clásica si Ollama no está disponible
- ✅ **Coverage: 89%** (requiere Ollama corriendo para cubrir happy path)

#### GET /api/excuses/ai
**Generación con Inteligencia Artificial** usando Ollama (soporta Llama, Mistral, CodeLlama, etc)

```bash
# Excusa generada con IA (contexto opcional)
curl -X GET "http://localhost:8080/api/excuses/ai"

# Con contexto específico
curl -X GET "http://localhost:8080/api/excuses/ai?context=microservicios en producción"
```

**Response 200:**
```json
{
  "id": 10,
  "contexto": "El microservicio de autenticación manejaba 50K requests/segundo sin rate limiting",
  "causa": "El equipo asumió que el proveedor de cloud auto-escalaría infinitamente",
  "consecuencia": "Los costos de AWS subieron 800% en 3 horas durante el Black Friday",
  "recomendacion": "Implementar circuit breaker con Resilience4j y límites de concurrencia por tenant",
  "meme": null,
  "ley": null,
  "roleTarget": null,
  "createdAt": "2024-01-15T10:40:00"
}
```

#### GET /api/excuses/ai/ultra
**MEGALODON ULTRA**: Excusa generada con IA + meme + ley (el máximo nivel 🔥🦈)

```bash
curl -X GET "http://localhost:8080/api/excuses/ai/ultra?context=deploy de viernes"
```

**Response 200:**
```json
{
  "id": 11,
  "contexto": "El viernes a las 17hs se desplegó un hotfix crítico en producción sin testing",
  "causa": "El PM insistió que era urgente y que 'solo eran 3 líneas de código'",
  "consecuencia": "El sistema entero cayó durante 4 horas afectando 100K usuarios activos",
  "recomendacion": "Establecer freeze window los viernes y proceso de rollback automatizado",
  "meme": "Mirá vos... commiteaste la contraseña de admin en el repo público",
  "ley": "MURPHY - Si algo puede salir mal, saldrá mal... justo antes del deploy de producción un viernes a las 17hs",
  "roleTarget": null,
  "createdAt": "2024-01-15T10:45:00"
}
```

**Nota:** Si Ollama no está disponible, automáticamente usa el generador clásico como fallback. Ver `TESTING_OLLAMA.md` para instrucciones de testing manual.

---

#### GET /api/excuses/role/{role}
Excusa dirigida a un rol específico

Roles válidos: `DEV`, `QA`, `DEVOPS`, `PM`, `ARCHITECT`, `DBA`

```bash
curl -X GET http://localhost:8080/api/excuses/role/DEVOPS
```

**Response 200:**
```json
{
  "id": 5,
  "contexto": "La base de datos estaba sin backups configurados",
  "causa": "El algoritmo de caching fue optimizado sin considerar edge cases",
  "consecuencia": "Los usuarios reportaron latencia de 5 segundos en cada solicitud",
  "recomendacion": "Hacer rollback y revisar con el equipo antes del próximo deploy",
  "meme": null,
  "ley": null,
  "roleTarget": "DEVOPS",
  "createdAt": "2024-01-15T10:36:30"
}
```

#### GET /api/excuses/{id}
Obtener excusa específica por ID

```bash
curl -X GET http://localhost:8080/api/excuses/1
```

#### GET /api/excuses
Listar todas las excusas generadas

```bash
curl -X GET http://localhost:8080/api/excuses
```

---

### Gestionar Fragmentos

#### GET /api/fragments
Listar todos los fragmentos

```bash
curl -X GET http://localhost:8080/api/fragments
```

#### GET /api/fragments/by-type?tipo=CONTEXTO
Fragmentos por tipo

Tipos válidos: `CONTEXTO`, `CAUSA`, `CONSECUENCIA`, `RECOMENDACION`

```bash
curl -X GET "http://localhost:8080/api/fragments/by-type?tipo=CONTEXTO"
```

#### GET /api/fragments/active
Solo fragmentos activos

```bash
curl -X GET http://localhost:8080/api/fragments/active
```

#### POST /api/fragments
Crear nuevo fragmento

```bash
curl -X POST http://localhost:8080/api/fragments \
  -H "Content-Type: application/json" \
  -d '{
    "type": "CONTEXTO",
    "text": "Nuevo contexto de excusa",
    "source": "CUSTOM",
    "category": "deployment"
  }'
```

**Response 201:**
```json
{
  "id": 9,
  "type": "CONTEXTO",
  "text": "Nuevo contexto de excusa",
  "source": "CUSTOM",
  "category": "deployment",
  "createdAt": "2024-01-15T10:40:00",
  "updatedAt": "2024-01-15T10:40:00"
}
```

#### PUT /api/fragments/{id}
Actualizar fragmento

```bash
curl -X PUT http://localhost:8080/api/fragments/9 \
  -H "Content-Type: application/json" \
  -d '{
    "type": "CAUSA",
    "text": "Contexto actualizado",
    "source": "CUSTOM-UPD",
    "category": "development"
  }'
```

#### DELETE /api/fragments/{id}
Eliminar fragmento

```bash
curl -X DELETE http://localhost:8080/api/fragments/9
```

**Response 204:** (Sin contenido, eliminación exitosa)

---

### Health Check

#### GET /health
Estado de la API

```bash
curl -X GET http://localhost:8080/health
```

**Response 200:**
```json
{
  "status": "UP",
  "service": "Excusas Shark API",
  "version": "1.0.0",
  "timestamp": 1705313445123
}
```

---

## 💻 Ejemplos de Uso

### PowerShell

```powershell
# Excusa aleatoria
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/excuses/random" `
    -Headers @{"Accept"="application/json"} `
    -Method Get

$response.Content | ConvertFrom-Json | Format-Table

# Ultra Shark
$ultra = Invoke-WebRequest -Uri "http://localhost:8080/api/excuses/ultra" `
    -Headers @{"Accept"="application/json"} `
    -Method Get

($ultra.Content | ConvertFrom-Json).excusa | Format-List
```

### Bash/Linux

```bash
# Excusa para QA
curl -s http://localhost:8080/api/excuses/role/QA | jq .

# Crear fragmento
curl -X POST http://localhost:8080/api/fragments \
  -H "Content-Type: application/json" \
  -d '{"type":"CONTEXTO","text":"Test","source":"CLI","category":"test"}' | jq .

# Listar todas las excusas
curl -s http://localhost:8080/api/excuses | jq .[0:2]
```

### Python

```python
import requests
import json

BASE_URL = "http://localhost:8080"

# 1. Generar excusa ultra
response = requests.get(f"{BASE_URL}/api/excuses/ultra")
ultra_shark = response.json()
print(json.dumps(ultra_shark, indent=2, ensure_ascii=False))

# 2. Crear fragmento
new_fragment = {
    "type": "RECOMENDACION",
    "text": "Hacer code review exhaustivo antes de deploy",
    "source": "PYTHON-API",
    "category": "quality"
}
response = requests.post(
    f"{BASE_URL}/api/fragments",
    json=new_fragment,
    headers={"Content-Type": "application/json"}
)
print(f"Fragmento creado: {response.json()}")

# 3. Generar excusa para rol
roles = ["DEV", "QA", "DEVOPS", "PM", "ARCHITECT", "DBA"]
for role in roles:
    response = requests.get(f"{BASE_URL}/api/excuses/role/{role}")
    excuse = response.json()
    print(f"\n{role}: {excuse['contexto']}")
```

---

## 🔧 Desarrollo Local

### Prerrequisitos

```bash
# Verificar Java 17
java -version

# Verificar Maven
mvn -v

# Opcional: Verificar Ollama (para nivel Megalodon)
ollama --version
```

### Compilar

```bash
# Build sin tests
mvn clean package -DskipTests

# Build con tests
mvn clean package

# Build con cobertura JaCoCo (89% coverage)
mvn clean package jacoco:report
# Ver reporte en: target/site/jacoco/index.html
```

**Nota sobre Coverage:**
- Coverage actual: **89%** (1,967/2,189 instructions)
- Para 100% se requeriría Ollama corriendo durante tests
- Happy path AI cubierto con fallback mocks

### Ejecutar

```bash
# Modo desarrollo
mvn spring-boot:run

# Con propiedades específicas
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"

# JAR directo
java -jar target/excusas-shark-1.0.0.jar
```

### Acceder a H2 Console

```
URL:       http://localhost:8080/h2-console
JDBC URL:  jdbc:h2:mem:excusasdb
User:      sa
Password:  (vacía)
```

---

## 🐳 Docker

### Build y Run con Docker

```bash
# Compilar Dockerfile (multistage)
docker build -t excusas-shark:latest .

# Ejecutar contenedor
docker run -p 8080:8080 excusas-shark:latest

# Con variables de entorno
docker run -p 8080:8080 \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  excusas-shark:latest
```

### Docker Compose

```bash
# Ejecutar servicios (solo API)
docker-compose up

# Ejecutar con perfil dev (API + H2 Console)
docker-compose --profile dev up

# Ver logs
docker-compose logs -f excusas-shark-api

# Detener
docker-compose down

# Limpiar volúmenes
docker-compose down -v
```

**URLs en Docker Compose:**
- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console (dev): `http://localhost:8082`

---

## 🎯 Patrones y Principios

### Clean Code

- ✅ Nombres descriptivos y autodocumentados
- ✅ Métodos pequeños con responsabilidad única
- ✅ DRY (Don't Repeat Yourself)
- ✅ KISS (Keep It Simple, Stupid)

### SOLID

| Principio | Aplicación |
|-----------|-----------|
| **S**ingle Responsibility | Cada controlador, servicio y repo tiene una responsabilidad |
| **O**pen/Closed | Extensible mediante herencia de repositorios |
| **L**iskov Substitution | Interfaces JpaRepository polimórficas |
| **I**nterface Segregation | DTOs específicos por operación (Request vs Response) |
| **D**ependency Inversion | Inyección de dependencias por constructor |

### Patrones Implementados

1. **Hexagonal Architecture** (Ports & Adapters)
   - Dominio independiente de frameworks
   - Fácil de testear

2. **DTO Pattern**
   - RequestDTO con validación
   - ResponseDTO para salida

3. **Mapper Pattern**
   - Estáticos, null-safe
   - Reutilizable

4. **Repository Pattern**
   - Spring Data JPA
   - Query derivados

5. **Service Layer**
   - Dos métodos por operación (Entity vs DTO)
   - Lógica de negocio centralizada

### Timestamps

Todas las entidades tienen timestamps automáticos:

```java
@PrePersist
void onCreate() {
    createdAt = LocalDateTime.now();
}

@PreUpdate
void onUpdate() {
    updatedAt = LocalDateTime.now();
}
```

---

## 📊 Estructura de Carpetas

```
excusas-shark/
├── src/
│   ├── main/
│   │   ├── java/com/excusasshark/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── dto/              # DTOs
│   │   │   ├── model/            # Entities y Enums
│   │   │   ├── service/          # Business Logic
│   │   │   ├── repository/       # JPA Repositories
│   │   │   ├── mapper/           # DTO Mappers
│   │   │   ├── config/           # Configuración
│   │   │   └── ExcusasSharkApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/excusasshark/
│           ├── service/          # Service Tests (Unit)
│           └── controller/       # Controller Tests (Integration)
├── docs/
│   └── uml/
│       ├── classes.puml
│       ├── sequence.puml
│       ├── components.puml
│       └── deployment.puml
├── pom.xml                       # Maven Build Config
├── Dockerfile                    # Multistage Build
├── docker-compose.yml            # Services Orchestration
├── README.md                     # Este archivo
└── .gitignore
```

---

## 🧪 Testing

### Unit Tests (Mockito)

```bash
mvn test -Dtest=ExcuseGeneratorServiceTest
mvn test -Dtest=FragmentServiceTest
```

### Integration Tests (RestAssured)

```bash
mvn test -Dtest=ExcuseControllerIT
mvn test -Dtest=FragmentControllerIT
```

### Coverage Report (JaCoCo)

```bash
mvn jacoco:report
open target/site/jacoco/index.html
```

---

## 📝 Licencia

MIT License - Ver `LICENSE` para detalles

---

## 👥 Contribuciones

Contribuciones bienvenidas. Por favor:

1. Fork el repositorio
2. Crea rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre Pull Request

---

## 📞 Contacto

- **Equipo**: Accenture Tech Team
- **Email**: tech@accenture.com
- **Website**: https://www.accenture.com

---

**¡Hecho con ❤️ por Accenture!**

*"Del Mojarrita al White Shark, generando excusas desde 2024"* 🦈
