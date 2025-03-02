# Используем другой образ Maven
FROM maven:3.8.6-eclipse-temurin-17

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY . .

# Сборка проекта
RUN mvn clean install

EXPOSE 8080
# Указываем команду для запуска приложения
CMD ["java", "-jar", "target/Server-0.0.1-SNAPSHOT.jar"]