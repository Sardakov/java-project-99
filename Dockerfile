FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

COPY build.gradle settings.gradle gradle.properties /app/
COPY gradle /app/gradle
COPY src /app/src

RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080