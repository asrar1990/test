FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY /target/device-service-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "device-service-1.0-SNAPSHOT.jar"]
EXPOSE 9002