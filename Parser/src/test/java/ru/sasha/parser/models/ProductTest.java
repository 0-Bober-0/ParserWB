package ru.sasha.parser.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Product.
 */
public class ProductTest {

    /**
     * Тест конструктора и геттеров/сеттеров.
     */
    @Test
    public void testProductConstructorAndGettersSetters() {
        // Создаем новый продукт через конструктор
        Product product = new Product(1, "Мокасины кожаные", "ROOMAN", 1718, 4.8,
                "https://www.wildberries.ru/catalog/241024982/detail.aspx");

        // Проверяем значения всех полей
        assertEquals(1, product.getId());
        assertEquals("Мокасины кожаные", product.getTitle());
        assertEquals("ROOMAN", product.getBrand());
        assertEquals(1718, product.getTotalReviews());
        assertEquals(4.8, product.getAverageScore());
        assertEquals("https://www.wildberries.ru/catalog/241024982/detail.aspx", product.getUrl());

        // Создаем пустой продукт и заполняем через сеттеры
        Product emptyProduct = new Product();
        emptyProduct.setId(2);
        emptyProduct.setTitle("Кроссовки спортивные");
        emptyProduct.setBrand("ADIDAS");
        emptyProduct.setTotalReviews(425);
        emptyProduct.setAverageScore(4.5);
        emptyProduct.setUrl("https://www.wildberries.ru/catalog/12345/detail.aspx");

        // Проверяем значения всех полей
        assertEquals(2, emptyProduct.getId());
        assertEquals("Кроссовки спортивные", emptyProduct.getTitle());
        assertEquals("ADIDAS", emptyProduct.getBrand());
        assertEquals(425, emptyProduct.getTotalReviews());
        assertEquals(4.5, emptyProduct.getAverageScore());
        assertEquals("https://www.wildberries.ru/catalog/12345/detail.aspx", emptyProduct.getUrl());
    }

    /**
     * Тест метода toString().
     */
    @Test
    public void testToString() {
        Product product = new Product(0, "Мокасины кожаные", "ROOMAN", 1718, 4.8,
                "https://www.wildberries.ru/catalog/241024982/detail.aspx");

        String expected = "{\"title\": \"Мокасины кожаные\", \"brand\": \"ROOMAN\", " +
                "\"total_reviews\": 1718, \"average_score\": 4.8, " +
                "\"url\": \"https://www.wildberries.ru/catalog/241024982/detail.aspx\"}";

        assertEquals(expected, product.toString());
    }
}
