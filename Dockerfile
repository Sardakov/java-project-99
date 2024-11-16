# Базовый образ для сборки
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

# Копируем Gradle файлы
COPY build.gradle settings.gradle gradle.properties /app/

# Копируем папку Gradle
COPY gradle /app/gradle

# Копируем исходный код
COPY src /app/src

# Сборка проекта
RUN ./gradlew build -x test --no-daemon

# Финальный образ для выполнения
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080