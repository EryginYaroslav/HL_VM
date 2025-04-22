FROM gradle:8.5-jdk21 AS builder

# Рабочая дирректория внутри контейнера
WORKDIR /home/hl/gradle/project

COPY --chown=gradle:gradle build.gradle settings.gradle gradle.properties? ./
COPY --chown=gradle:gradle gradlew gradle/ ./gradle/

# Прогружаем зависимости
RUN ./gradlew --no-daemon dependencies

# Копируем весь проект
COPY --chown=gradle:gradle . .

# Собираем fat‑jar (Spring Boot BootJar)
RUN ./gradlew --no-daemon clean bootJar


FROM eclipse-temurin:21-jre-jammy

# Рабочая директория для рантайма
WORKDIR /app

# Копируем собранный JAR из билдера
COPY --from=builder /home/hl/gradle/project/build/libs/*.jar ./app.jar

# Открываем порт приложения
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
