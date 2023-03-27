# syntax=docker/dockerfile:1.2
FROM --platform=linux/amd64 ubuntu:20.04 AS builder
RUN apt-get update && apt-get install -y openjdk-11-jdk
ENV PATH="/usr/local/bin:$PATH"
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw package
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/omo-0.0.1-SNAPSHOT.jar /app/omo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/omo-0.0.1-SNAPSHOT.jar"]