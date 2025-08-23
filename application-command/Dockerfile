FROM gradle:8.12.1-jdk21 AS builder
COPY .. /command
WORKDIR /command
RUN gradle clean application-command:bootJar

FROM openjdk:21-jdk-slim
COPY --from=builder /command/application-command/build/libs/*.jar command.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "command.jar"]