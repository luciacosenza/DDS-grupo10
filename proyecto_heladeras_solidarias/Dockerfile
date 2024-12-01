FROM maven:3-eclipse-temurin-22-alpine AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:22-jdk-slim
COPY --from=build /target/proyecto_heladeras_solidarias-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","proyect_heladeras_solidarias.jar"]