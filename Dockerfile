# ───────────────────────────────────────────────
# 1) Build stage
# ───────────────────────────────────────────────
FROM gradle:8.5-jdk21 AS builder

# Рабочая директория (у образа gradle по умолчанию есть /home/gradle)
WORKDIR /home/gradle/project

# Копируем только gradle wrapper и основные скрипты в корень
COPY --chown=gradle:gradle gradlew gradlew.bat settings.gradle build.gradle ./
COPY --chown=gradle:gradle gradle/wrapper gradle/wrapper

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Скачиваем зависимости (кеширование)
RUN ./gradlew --no-daemon dependencies

# Копируем весь проект (src и всё остальное)
COPY --chown=gradle:gradle . .

# Собираем fat‑jar
RUN ./gradlew --no-daemon clean bootJar

# ───────────────────────────────────────────────
# 2) Runtime stage
# ───────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Копируем jar из билдера
COPY --from=builder /home/gradle/project/build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
