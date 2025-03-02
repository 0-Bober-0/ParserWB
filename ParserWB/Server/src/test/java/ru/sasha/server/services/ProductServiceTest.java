package ru.sasha.server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sasha.server.models.Products;
import ru.sasha.server.repositories.ProductsRepository;
import ru.sasha.server.services.producer.Producer;
import ru.sasha.server.services.producer.ProducerTest;

import static org.mockito.Mockito.*;

/**
 * Тесты для сервиса ProductService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductsRepository productRepository;

    @Mock
    private Producer producer;

    @InjectMocks
    private ProductService productService;

    private Products testProduct;

    /**
     * Настройка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        testProduct = new Products();
        testProduct.setId(1);
        testProduct.setTitle("Тестовый продукт");
        testProduct.setBrand("Тестовый бренд");
        testProduct.setTotalReviews(100);
        testProduct.setAverageScore(4.5);
        testProduct.setUrl("https://www.wildberries.ru/catalog/12345/detail.aspx");
    }

    /**
     * Тест метода save().
     */
    @Test
    public void testSave() {
        // Настройка мока
        when(productRepository.save(any(Products.class))).thenReturn(testProduct);

        // Вызов тестируемого метода
        productService.saveOrUpdateProduct(testProduct);

        // Проверка, что метод репозитория был вызван
        verify(productRepository, times(1)).save(testProduct);
    }

    /**
     * Тест метода createFoodOrder().
     */
    @Test
    public void testCreateFoodOrder() throws JsonProcessingException {
        // Вызов тестируемого метода
        productService.create(testProduct);

        // Проверка, что метод producer.sendMessage был вызван с правильным аргументом
        verify(producer, times(1)).sendMessage(testProduct);
    }
}
