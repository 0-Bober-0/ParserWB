package ru.sasha.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения для запуска Spring Boot.
 *
 * @author Alexandr Daev
 */
@SpringBootApplication
public class ParserApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ParserApplication.class, args);
        LOGGER.info("Приложение запущено успешно.");
    }
}
