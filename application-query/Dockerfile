FROM gradle:8.12.1-jdk21 as builder
COPY .. /query
WORKDIR /query
RUN gradle clean application-query:bootJar

FROM openjdk:21-jdk-slim
COPY --from=builder /query/application-query/build/libs/*.jar query.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "query.jar"]
