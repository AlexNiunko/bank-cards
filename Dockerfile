FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/bank_rest-3.2.5.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]