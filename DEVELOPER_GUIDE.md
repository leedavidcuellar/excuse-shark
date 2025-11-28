# DEVELOPER_GUIDE.md - Guía del Desarrollador

## 🚀 Primeros Pasos

### 1. Clonar y Preparar

```bash
# Clonar repositorio
git clone https://github.com/accenture/excusas-shark.git
cd excusas-shark

# Verificar Java 17
java -version
# Debe mostrar: openjdk version "17.x.x" o superior

# Verificar Maven
mvn -v
# Debe mostrar: Apache Maven 3.9+
```

### 2. Build Inicial

```bash
# Limpiar y compilar
mvn clean install

# Ejecutar tests
mvn test

# Ver reporte de cobertura
mvn jacoco:report
open target/site/jacoco/index.html
```

### 3. Ejecutar en Local

```bash
# Terminal 1: Arrancar aplicación
mvn spring-boot:run

# Terminal 2: Verificar health
curl http://localhost:8080/health

# Terminal 3: Generar excusa
curl http://localhost:8080/api/excuses/random | json_pp
```

---

## 📁 Estructura de Proyecto

```
excusas-shark/
├── src/
│   ├── main/
│   │   ├── java/com/excusasshark/
│   │   │   ├── controller/       ← HTTP Adapters
│   │   │   ├── dto/              ← DTOs con validación
│   │   │   ├── model/            ← Entities
│   │   │   ├── service/          ← Lógica de negocio
│   │   │   ├── repository/       ← JPA Adapters
│   │   │   ├── mapper/           ← Entity ↔ DTO mapping
│   │   │   ├── config/           ← Spring configuration
│   │   │   └── ExcusasSharkApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/excusasshark/
│           ├── service/          ← Unit tests (Mockito)
│           └── controller/       ← Integration tests (RestAssured)
├── docs/
│   └── uml/                      ← PlantUML diagrams
├── pom.xml                       ← Maven config
├── Dockerfile                    ← Multistage build
├── docker-compose.yml            ← Services orchestration
├── README.md                     ← Documentación principal
├── RESUMEN_SESION.md             ← Resumen ejecutivo
├── GUIA_CALIDAD_CODIGO.md        ← Quality standards
└── .gitignore
```

---

## 🔧 Tareas Comunes

### Agregar un Nuevo Endpoint

#### 1. Crear DTO (si es necesario)

```java
// src/main/java/.../dto/NewRequestDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email
    private String email;
}

@Data
@Builder
public class NewResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
```

#### 2. Crear Service Method

```java
// Agregar a src/main/java/.../service/SomeService.java
public NewResponseDTO processNew(NewRequestDTO dto) {
    // Lógica de negocio
    Entity entity = NewMapper.toEntity(dto);
    Entity saved = repository.save(entity);
    return NewMapper.toResponse(saved);
}
```

#### 3. Agregar Mapper

```java
// src/main/java/.../mapper/NewMapper.java
public class NewMapper {
    private NewMapper() {}
    
    public static Entity toEntity(NewRequestDTO dto) {
        if (dto == null) return null;
        return Entity.builder()
                .name(dto.getName())
                .build();
    }
    
    public static NewResponseDTO toResponse(Entity entity) {
        if (entity == null) return null;
        return NewResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
```

#### 4. Agregar Endpoint en Controller

```java
// Agregar a src/main/java/.../controller/SomeController.java
@PostMapping
@Operation(summary = "Create new")
@ApiResponse(responseCode = "201", description = "Created")
public ResponseEntity<NewResponseDTO> create(
        @Valid @RequestBody NewRequestDTO dto) {
    NewResponseDTO result = service.processNew(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}
```

#### 5. Agregar Test

```java
// src/test/java/.../controller/SomeControllerIT.java
@Test
void shouldCreateNew() {
    String requestBody = """
        {
            "name": "Test",
            "email": "test@test.com"
        }
        """;
    
    given()
        .contentType(JSON)
        .body(requestBody)
    .when()
        .post("/api/new")
    .then()
        .statusCode(201)
        .body("name", equalTo("Test"));
}
```

---

### Agregar Fragmento de Datos

```bash
# 1. Ir a DataLoaderConfig.java
# 2. Agregar al CommandLineRunner:

fragmentService.create(Fragment.builder()
    .type(FragmentType.CONTEXTO)
    .text("Nuevo fragmento de contexto")
    .source("DEVELOPER")
    .category("custom")
    .build());

# 3. Compilar y ejecutar
mvn spring-boot:run

# 4. Verificar en H2 Console
# http://localhost:8080/h2-console
```

---

### Ejecutar Tests Específicos

```bash
# Tests de un servicio
mvn test -Dtest=ExcuseGeneratorServiceTest

# Tests de un método específico
mvn test -Dtest=ExcuseGeneratorServiceTest#testGenerateRandomExcuse

# Tests de integración solamente
mvn test -Dtest=*IT

# Con debug
mvn test -Dtest=ExcuseGeneratorServiceTest -X
```

---

### Generar Report de Cobertura

```bash
# Ejecutar tests con JaCoCo
mvn clean test jacoco:report

# Abrir reporte
# Mac
open target/site/jacoco/index.html

# Windows (PowerShell)
start target/site/jacoco/index.html

# Linux
firefox target/site/jacoco/index.html
```

---

### Usar H2 Console

```
1. Arrancar aplicación:
   mvn spring-boot:run

2. Abrir navegador:
   http://localhost:8080/h2-console

3. Configurar conexión:
   - JDBC URL: jdbc:h2:mem:excusasdb
   - User: sa
   - Password: (deixar vacío)

4. Hacer click en Connect

5. Ejecutar queries:
   SELECT * FROM fragments;
   SELECT * FROM excuses;
   SELECT * FROM memes;
   SELECT * FROM laws;
```

---

## 🐛 Troubleshooting

### ❌ "Cannot find JDK 17"

**Solución:**
```bash
# Verificar instalación
java -version

# Si no está instalado
# Windows: descargar de https://adoptopenjdk.net
# Mac: brew install openjdk@17
# Linux: apt-get install openjdk-17-jdk

# Configurar JAVA_HOME
export JAVA_HOME=/path/to/jdk17
```

### ❌ "Maven not found"

**Solución:**
```bash
# Verificar instalación
mvn -version

# Si no está: descargar de https://maven.apache.org
# O usar: brew install maven (Mac)

# Configurar MAVEN_HOME
export MAVEN_HOME=/path/to/maven
export PATH=$PATH:$MAVEN_HOME/bin
```

### ❌ "Port 8080 already in use"

**Solución:**
```bash
# Opción 1: Cambiar puerto en application.properties
server.port=9090

# Opción 2: Matar proceso en puerto 8080
# Linux/Mac
lsof -ti :8080 | xargs kill -9

# Windows
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process
```

### ❌ "Tests failing with SQLException"

**Solución:**
```bash
# Clean cache
mvn clean

# Reconstruir
mvn package -DskipTests

# Ejecutar tests después
mvn test
```

### ❌ "Gradle/Maven dependency issues"

**Solución:**
```bash
# Limpiar cache local
mvn clean

# Actualizar dependencies
mvn dependency:resolve

# Tree de dependencias
mvn dependency:tree
```

### ❌ "Docker build fails"

**Solución:**
```bash
# Verificar Docker
docker --version

# Build sin cache
docker build --no-cache -t excusas-shark .

# Ver logs
docker build -t excusas-shark . 2>&1 | tail -50

# Verificar JAR existe
ls -la target/*.jar
```

### ❌ "docker-compose fails"

**Solución:**
```bash
# Verificar Docker Compose
docker-compose version

# Validar sintaxis
docker-compose config

# Limpiar y reconstruir
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

---

## 🔄 Workflow Típico de Desarrollo

### 1. Feature Branch

```bash
# Crear branch
git checkout -b feature/new-endpoint

# Hacer cambios
# ... editar archivos ...

# Compilar
mvn clean compile

# Tests locales
mvn test

# Commit
git add .
git commit -m "feat: add new endpoint for excuse generation"
```

### 2. Push y Pull Request

```bash
# Hacer push
git push origin feature/new-endpoint

# Crear PR en GitHub
# - Agregar descripción
# - Linkar issues relacionados
# - Asegurar que todos los tests pasan
```

### 3. Code Review

```bash
# Si hay cambios solicitados:
git add .
git commit -m "review: address feedback"
git push origin feature/new-endpoint
```

### 4. Merge

```bash
# Una vez aprobado, merge
# Opción 1: Via GitHub UI
# Opción 2: Via CLI
git merge feature/new-endpoint
git push origin main
```

---

## 📊 SonarQube Integration

### Configurar SonarQube Local (Opcional)

```bash
# Descargar SonarQube Community Edition
# https://www.sonarqube.org/downloads/

# Iniciar servidor
./sonarqube/bin/linux-x86-64/sonar.sh start

# Acceder a http://localhost:9000
# Credenciales default: admin/admin

# Ejecutar análisis
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=excusas-shark \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin
```

---

## 📚 Recursos y Referencias

### Documentación Oficial
- [Spring Boot 3.2](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [RestAssured](https://rest-assured.io/)

### Libros Recomendados
- Clean Code - Robert C. Martin
- Refactoring - Martin Fowler
- SOLID Principles - Robert C. Martin
- Spring in Action - Craig Walls

### Online Courses
- Spring Boot Microservices - Udemy
- Test-Driven Development - Pluralsight
- Docker & Kubernetes - Udemy

---

## 🎓 Tips y Tricks

### Debug en IDE

```java
// Breakpoint
// 1. Click en el margen izquierdo (línea)
// 2. Ejecutar en modo debug
mvn spring-boot:run -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005

// 3. Conectar debugger en IDE
// 4. F6 para step over, F5 para step into
```

### Hot Reload

```bash
# Con Spring DevTools (ya incluido en pom.xml)
# Los cambios se aplican automáticamente

# Cambiar servidor.xml en IDE
# Presionar Ctrl+F10 (idea) o similar

# Ver configuración en application.properties:
# spring.devtools.restart.enabled=true
```

### Performance Profiling

```bash
# Con JProfiler o YourKit
java -agentpath:/path/to/jprofiler/bin/linux-x64/libjprofilerti.so=port=8849 \
     -jar target/excusas-shark-1.0.0.jar

# Ver en http://localhost:8849
```

---

## ✅ Pre-commit Checklist

Antes de hacer commit:

- [ ] Código compila (`mvn clean compile`)
- [ ] Tests pasan (`mvn test`)
- [ ] Cobertura > 95% (`mvn jacoco:report`)
- [ ] No hay warnings de SonarQube
- [ ] Código está formateado
- [ ] No hay println/System.out
- [ ] No hay credenciales hardcodeadas
- [ ] DTOs tienen @Valid
- [ ] Services tienen @Transactional si es necesario
- [ ] Controllers tienen @Operation (Swagger)

---

## 🚀 Deploy a Producción

### Build Dockerfile

```bash
# Compilar jar
mvn clean package

# Build Docker image
docker build -t excusas-shark:1.0.0 .

# Tag para registry
docker tag excusas-shark:1.0.0 docker.io/lee-cuellar-acc/excusas-shark:1.0.0

# Push to registry
docker push docker.io/lee-cuellar-acc/excusas-shark:1.0.0
```

### Deploy con Docker Compose

```bash
# Preparar docker-compose.yml para producción
# - Cambiar networks (usar red existente)
# - Agregar volume para logs
# - Configurar health checks

docker-compose -f docker-compose.prod.yml up -d
```

---

**¡Estás listo para desarrollar en Excusas Shark!** 🦈

*Para preguntas, ver README.md o contactar al equipo*
