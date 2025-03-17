# Используем образ с Java и Chrome
FROM selenium/standalone-chrome:latest

# Устанавливаем Java (если необходимо)
USER root
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Устанавливаем Maven
RUN apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY . .

# Сборка проекта
RUN mvn clean install

# Указываем порт
EXPOSE 8081

# Указываем команду для запуска приложения
CMD ["java", "-jar", "target/Parser-0.0.1-SNAPSHOT.jar"]
