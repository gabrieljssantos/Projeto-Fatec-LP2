# Use a imagem oficial do Maven para compilar e construir o projeto
FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Use a imagem oficial do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Instala o Python
RUN apt-get update && apt-get install -y python3

COPY --from=build /app/target/spring3app-0.0.1-SNAPSHOT.jar /app/spring3app.jar
ENTRYPOINT ["java", "-jar", "/app/spring3app.jar"]
