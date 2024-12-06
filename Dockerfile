FROM maven:3-eclipse-temurin-22-alpine AS build
COPY ../../.. .
RUN mvn clean package -DskipTests
FROM openjdk:22-jdk-slim
COPY .env .env
COPY --from=build /target/proyecto_heladeras_solidarias-1.0-SNAPSHOT.jar proyecto_heladeras_solidarias.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","proyecto_heladeras_solidarias.jar"]