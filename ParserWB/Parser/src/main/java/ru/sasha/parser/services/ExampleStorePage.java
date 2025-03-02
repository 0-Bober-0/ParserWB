package ru.sasha.parser.services;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sasha.parser.core.PageNavigator;

/**
 * Реализация навигатора для магазина ExampleStore.
 *
 * @author Alexandr Daev
 */
public class ExampleStorePage implements PageNavigator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleStorePage.class);

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public void scrollPage(WebDriver driver) {
        LOGGER.info("Начало прокрутки страницы");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastScrollPosition = 0;

        while (true) {
            js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warn("Поток был прерван во время ожидания");
                break;
            }

            long currentScrollPosition = (long) js.executeScript("return document.documentElement.scrollHeight");
            if (currentScrollPosition == lastScrollPosition) {
                LOGGER.info("Достигнут конец страницы. Прекращение прокрутки.");
                break;
            }
            lastScrollPosition = currentScrollPosition;
        }
    }
}
