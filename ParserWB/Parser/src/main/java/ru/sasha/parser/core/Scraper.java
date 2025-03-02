package ru.sasha.parser.core;

import java.util.List;
import org.openqa.selenium.WebDriver;
import ru.sasha.parser.models.Product;


/**
 * Абстрактный класс для реализации скрейпера.
 *
 * @author Alexandr Daev
 */
public abstract class Scraper {

    protected WebDriver driver;

    /**
     * Конструктор для инициализации WebDriver.
     *
     * @param driver WebDriver для управления браузером
     */
    public Scraper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Скрейпит данные с переданного URL.
     *
     * @param url URL для скрапинга
     * @return список продуктов
     * @throws InterruptedException если поток прерывается во время работы
     */
    public abstract List<Product> scrape(String url) throws InterruptedException;
}
