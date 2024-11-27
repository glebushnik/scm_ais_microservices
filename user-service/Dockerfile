FROM debian
# Используем образ OpenJDK на базе Debian для совместимости с ARM
FROM openjdk:17-jdk-slim

# Информация об авторе
LABEL authors="glebushnik"

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл
COPY build/libs/*.jar app.jar

# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
