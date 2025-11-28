# Excusas Shark API - Multistage Docker Build
# Stage 1: Build stage using Maven
FROM maven:3.9.11-eclipse-temurin-17 AS builder

LABEL maintainer="Accenture Tech Team"
LABEL description="Build stage for Excusas Shark API"

WORKDIR /build

# Copy pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Compilar y construir el JAR
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime stage using Java 17 Slim
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="Accenture Tech Team"
LABEL description="Runtime stage for Excusas Shark API"

# Crear usuario no-root por seguridad
RUN addgroup -g 1000 appuser && adduser -D -u 1000 -G appuser appuser

WORKDIR /app

# Copiar el JAR desde el stage de build
COPY --from=builder /build/target/excusas-shark-*.jar app.jar

# Cambiar permisos
RUN chown -R appuser:appuser /app && chmod +x app.jar

# Cambiar a usuario no-root
USER appuser

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/health || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Configuración de memoria (puede ser sobreescrita)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
