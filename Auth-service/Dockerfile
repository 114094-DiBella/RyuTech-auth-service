# Dockerfile simple para RyuTech Auth Service
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR generado por Maven/Gradle
COPY target/auth-service.jar app.jar

# Exponer puerto para la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]