FROM openjdk:11-jre-slim

WORKDIR /app

COPY ./build/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
