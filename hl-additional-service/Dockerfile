# 1) Собираем jar с помощью Gradle
FROM gradle:8.5-jdk21 AS builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar --no-daemon

# 2) Запускаем собранный jar на JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
