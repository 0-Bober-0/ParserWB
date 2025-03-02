package ru.sasha.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения Spring Boot.
 * Точка входа в приложение.
 *
 * @author Alexandr Daev
 */
@SpringBootApplication  // Включает автоконфигурацию Spring Boot
public class ServerApplication {

    /**
     * Главный метод, запускающий приложение Spring Boot.
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
