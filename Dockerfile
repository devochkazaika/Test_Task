# Используем Maven с JDK для сборки и запуска
FROM maven:3.8.6-openjdk-11-slim AS builder

WORKDIR /app

COPY ./pom.xml /app/
COPY ./src /app/src/

# Собираем проект
RUN mvn clean install

# Используем тот же образ для запуска приложения
FROM maven:3.8.6-openjdk-11-slim

COPY . .

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

RUN mvn clean install
# Выполняем команду mvn exec:java для запуска Main
ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=org.cwt.task.Main"]
