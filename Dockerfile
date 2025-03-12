FROM maven:3.8.6-openjdk-11-slim AS builder

WORKDIR /app

COPY ./pom.xml /app/
COPY ./src /app/src/

# Собираем проект
RUN mvn clean install

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV LIBRARY_PORT=${LIBRARY_PORT}

# Выполняем команду mvn exec:java для запуска Main
ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=org.cwt.task.Main"]
