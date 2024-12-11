FROM openjdk:17-jdk-slim AS builder

WORKDIR /build
COPY . .
RUN ./gradlew clean build -x test
FROM openjdk:17-jdk-slim AS runner

WORKDIR /app
COPY --from=builder /build/build/libs/gateway-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
