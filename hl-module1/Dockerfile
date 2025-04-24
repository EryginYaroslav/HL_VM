# ───────────────────────────────────────────────
# 1) Build stage
# ───────────────────────────────────────────────
FROM gradle:8.5-jdk21 AS builder

WORKDIR /home/gradle/project

# Копируем весь проект
COPY . .

# Собираем fat‑jar через встроенный gradle
RUN gradle clean bootJar --no-daemon

# ───────────────────────────────────────────────
# 2) Runtime stage
# ───────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Копируем готовый JAR из билдера
COPY --from=builder /home/gradle/project/build/libs/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
