package ru.sasha.parser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sasha.parser.core.Scraper;
import ru.sasha.parser.models.Product;
import ru.sasha.parser.send.DataSend;

/**
 * Класс для запуска скрейпера с заданным URL.
 *
 * @author Alexandr Daev
 */
@Service
@NoArgsConstructor
public class ProductScraperService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductScraperService.class);
    private static DataSend fileDataWriter;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param fileDataWriter сервис для записи данных продуктов
     */
    @Autowired
    public ProductScraperService(DataSend fileDataWriter) {
        ProductScraperService.fileDataWriter = fileDataWriter;
    }

    /**
     * Запускает скрейпер по-заданному URL.
     *
     * @param startUrl URL, с которого начнется парсинг
     */
    public static void runScraper(String startUrl) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");


        WebDriver driver = new ChromeDriver(options);
        try {
            Scraper scraper = new ExampleStoreScraper(driver);
            List<Product> products = scraper.scrape(startUrl);
            fileDataWriter.writeProducts(products);
            LOGGER.info("Парсинг завершен. Найдено {} товаров.", products.size());
        } catch (InterruptedException e) {
            LOGGER.error("Процесс парсинга был прерван: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка при обработке данных в JSON: {}", e.getMessage());
        }
    }
}
