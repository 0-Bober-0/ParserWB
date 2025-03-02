package ru.sasha.server.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для модели Products.
 */
public class ProductsTest {

    /**
     * Проверяет корректность работы конструктора и геттеров/сеттеров.
     */
    @Test
    public void testProductConstructorAndGettersSetters() {
        // Создаем продукт
        Products product = new Products();
        product.setId(1);
        product.setTitle("Тестовый продукт");
        product.setBrand("Тестовый бренд");
        product.setTotalReviews(100);
        product.setAverageScore(4.5);
        product.setUrl("https://www.wildberries.ru/catalog/12345/detail.aspx");

        // Проверяем, что все поля установлены правильно
        assertEquals(1, product.getId());
        assertEquals("Тестовый продукт", product.getTitle());
        assertEquals("Тестовый бренд", product.getBrand());
        assertEquals(100, product.getTotalReviews());
        assertEquals(4.5, product.getAverageScore());
        assertEquals("https://www.wildberries.ru/catalog/12345/detail.aspx", product.getUrl());
    }

    /**
     * Проверяет корректность метода toString().
     */
    @Test
    public void testToString() {
        Products product = new Products();
        product.setTitle("Мокасины кожаные");
        product.setBrand("ROOMAN");
        product.setTotalReviews(1718);
        product.setAverageScore(4.8);
        product.setUrl("https://www.wildberries.ru/catalog/241024982/detail.aspx");

        String expected = "{\"title\": \"Мокасины кожаные\", \"brand\": \"ROOMAN\", " +
                "\"total_reviews\": 1718, \"average_score\": 4.8, " +
                "\"url\": \"https://www.wildberries.ru/catalog/241024982/detail.aspx\"}";

        assertEquals(expected, product.toString());
    }
}
