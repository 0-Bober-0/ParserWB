package ru.sasha.parser.services;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sasha.parser.core.Scraper;
import ru.sasha.parser.models.Product;

/**
 * Реализация скрейпера для магазина Wildberries.
 *
 * @author Alexandr Daev
 */
public class ExampleStoreScraper extends Scraper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleStoreScraper.class);

    public ExampleStoreScraper(WebDriver driver) {
        super(driver);
    }

    @Override
    public List<Product> scrape(String url) throws InterruptedException {
        LOGGER.info("Начало скрапинга страницы: {}", url);
        driver.get(url);

        new ExampleStorePage().scrollPage(driver);

        List<Product> products = new ArrayList<>();
        List<WebElement> productElements = driver.findElements(By.xpath(
                "//div[contains(@class, 'product-card__wrapper') and "
                        + "not(ancestor::div[contains(@class, 'custom-slider')])]"));
        LOGGER.info("Найдено {} элементов продукта.", productElements.size());

        for (WebElement element : productElements) {
            try {
                Product product = new Product();

                WebElement linkElement = element.findElement(By.className("product-card__link"));
                product.setUrl(linkElement.getAttribute("href"));

                WebElement nameElement = element.findElement(By.className("product-card__name"));
                String nameText = nameElement.getText();

                product.setTitle(nameText.substring(2).trim()); // Trim to avoid leading/trailing spaces

                WebElement brandElement = element.findElement(By.className("product-card__brand"));
                product.setBrand(brandElement.getText());

                WebElement ratingElement = element.findElement(By.className("address-rate-mini"));

                if (!ratingElement.getText().isEmpty()) {
                    product.setAverageScore(Double.parseDouble(ratingElement.getText().replace(",", ".")));

                    WebElement reviewsElement = element.findElement(By.className("product-card__count"));
                    String reviewsText = reviewsElement.getText().replaceAll("([\\d\\s]+)\\s*(оцен[а-я]+)$", "$1")
                            .replaceAll("\\s+", "");
                    product.setTotalReviews(Integer.parseInt(reviewsText));
                } else {
                    product.setAverageScore(0);
                    product.setTotalReviews(0);
                }

                products.add(product);
            } catch (Exception e) {
                LOGGER.error("Ошибка при обработке элемента продукта: {}", e.getMessage(), e);
                return products;
            }
        }
        LOGGER.info("Скрапинг завершен. Найдено {} товаров.", products.size());
        return products;
    }
}
