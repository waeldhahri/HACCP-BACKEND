


#
##17
### Étape 1 : build de l'application
#FROM maven:3.8.5-openjdk-17 AS builder
### FROM maven:3.8.5-eclipse-temurin-17 AS builder
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
### Étape 2 : image finale
# FROM openjdk:17-jdk-slim
### FROM eclipse-temurin:17-jdk-jammy
#VOLUME /tmp
#COPY --from=builder /app/target/*.jar app.jar
#
### ✅ Copier l'image dans le conteneur
#COPY src/main/resources/static/haccp.png /app/images/haccp.png
#ENTRYPOINT ["java", "-jar", "/app.jar"]


#
## Étape 1 : build de l'application
#FROM maven:3.8.5-eclipse-temurin-17 AS builder
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## Étape 2 : image finale
#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY --from=builder /app/target/*.jar app.jar
#
## ✅ Copier l'image dans le conteneur
#COPY src/main/resources/static/haccp.png /app/images/haccp.png
#ENTRYPOINT ["java", "-jar", "/app.jar"]



## Étape 1 : Build de l’application avec Maven
#FROM maven:3.8.5-eclipse-temurin-17 AS builder
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## Étape 2 : Image finale (légère)
#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#VOLUME /tmp
#
## Copier le jar
#COPY --from=builder /app/target/*.jar app.jar
#
## Copier les fichiers statiques nécessaires
#COPY src/main/resources/static/haccp.png /app/images/haccp.png
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]



#####################################################

# Étape 1 : build de l'application
FROM maven:3.8.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
COPY src/main/resources/static/haccp.png images/haccp.png
ENTRYPOINT ["java", "-jar", "app.jar"]
