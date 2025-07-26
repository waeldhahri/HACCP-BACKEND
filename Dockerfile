




# Étape 1 : build de l'application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar

# ✅ Copier l'image dans le conteneur
COPY src/main/resources/static/haccp.png /app/images/haccp.png
ENTRYPOINT ["java", "-jar", "/app.jar"]

