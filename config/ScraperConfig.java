package ru.sasha.parser.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для загрузки конфигурации из файла scraper.properties.
 * @author Alexandr Daev
 */
@NoArgsConstructor
public class ScraperConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperConfig.class);
    private static final String CONFIG_FILE = "scraper.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ScraperConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                LOGGER.error("Не удалось найти файл конфигурации: {}", CONFIG_FILE);
            } else {
                PROPERTIES.load(input);
                LOGGER.info("Конфигурация загружена из файла {}", CONFIG_FILE);
            }
        } catch (IOException ex) {
            LOGGER.error("Ошибка при загрузке конфигурации из файла {}: {}", CONFIG_FILE, ex.getMessage(), ex);
        }
    }

    /**
     * Возвращает значение свойства по его ключу.
     *
     * @param key ключ свойства
     * @return значение свойства или null, если ключ не найден
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
