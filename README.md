# Автоматизированная Информационная Система "Управление Цепочками Поставок"

## Описание проекта

Данный проект представляет собой серверную часть Автоматизированной Информационной Системы (АИС) для управления цепочками поставок. Система разработана с использованием современных технологий, таких как Spring Cloud, Spring Gateway, Eureka Discovery, Apache Kafka, PostgreSQL и Docker. Она обеспечивает масштабируемость, гибкость и надёжность, а также включает инструменты для мониторинга и управления данными (pgAdmin, Kafka UI).

## Установка и запуск

Для запуска проекта необходимо установить Docker и Docker Compose. После этого выполните несколько простых шагов.

### Установка Docker и Docker Compose

#### Для Windows:
1. Скачайте и установите [Docker Desktop](https://www.docker.com/products/docker-desktop).
2. Убедитесь, что Docker Compose установлен (он входит в состав Docker Desktop).
3. Откройте командную строку (CMD) или PowerShell.

#### Для Linux/macOS:
1. Установите Docker, следуя официальной документации:
   - Для Linux: [Install Docker Engine](https://docs.docker.com/engine/install/)
   - Для macOS: [Docker Desktop for Mac](https://docs.docker.com/docker-for-mac/install/)
2. Установите Docker Compose:
   - Для Linux/macOS: [Install Docker Compose](https://docs.docker.com/compose/install/)
3. Откройте терминал.

### Запуск проекта

1. Клонируйте репозиторий проекта:
   ```bash
   git clone https://github.com/glebushnik/scm_ais_microservices.git

2. Перейдите в корневую директорию проекта:
   ```bash
   cd scm_ais_microservices
3. Запустите проект с помощью Docker Compose:
   ```bash
   docker-compose up --build

### Swagger-UI доступен по http://localhost:8080/swagger-ui.html
