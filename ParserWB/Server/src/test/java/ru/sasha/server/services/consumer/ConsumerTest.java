package ru.sasha.server.services.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.sasha.server.models.Products;
import ru.sasha.server.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Тесты для Consumer (компонент приема сообщений из Kafka).
 * @author Daev Alexander
 */
@ExtendWith(MockitoExtension.class)
public class ConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private Consumer consumer;

    private String testJson;
    private List<Products> testProducts;

    /**
     * Настройка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setup() {
        // Подготовка тестовых данных
        testProducts = new ArrayList<>();

        Products product1 = new Products();
        product1.setId(1);
        product1.setTitle("Мокасины кожаные");
        product1.setBrand("ROOMAN");
        product1.setTotalReviews(1718);
        product1.setAverageScore(4.8);
        product1.setUrl("https://www.wildberries.ru/catalog/241024982/detail.aspx");

        Products product2 = new Products();
        product2.setId(2);
        product2.setTitle("Мокасины летние");
        product2.setBrand("STARRIER");
        product2.setTotalReviews(1799);
        product2.setAverageScore(4.8);
        product2.setUrl("https://www.wildberries.ru/catalog/195302872/detail.aspx");

        testProducts.add(product1);
        testProducts.add(product2);

        testJson = "[{\"id\":1,\"title\":\"Мокасины кожаные\",\"brand\":\"ROOMAN\"," +
                "\"total_reviews\":1718,\"average_score\":4.8," +
                "\"url\":\"https://www.wildberries.ru/catalog/241024982/detail.aspx\"}," +
                "{\"id\":2,\"title\":\"Мокасины летние\",\"brand\":\"STARRIER\"," +
                "\"total_reviews\":1799,\"average_score\":4.8," +
                "\"url\":\"https://www.wildberries.ru/catalog/195302872/detail.aspx\"}]";
    }

    /**
     * Тест метода consumeMessage().
     */
    @Test
    public void testConsumeMessage() throws JsonProcessingException {
        // Настройка моков
        when(objectMapper.readValue(eq(testJson), any(TypeReference.class))).thenReturn(testProducts);
        doNothing().when(productService).saveOrUpdateProduct(any(Products.class));

        // Вызов тестируемого метода
        consumer.consumeMessage(testJson);

        // Проверка, что методы моков были вызваны с правильными аргументами
        verify(objectMapper, times(1)).readValue(eq(testJson), any(TypeReference.class));
        verify(productService, times(2)).saveOrUpdateProduct(any(Products.class));
        verify(productService, times(1)).saveOrUpdateProduct(testProducts.get(0));
        verify(productService, times(1)).saveOrUpdateProduct(testProducts.get(1));
    }

    /**
     * Тест обработки пустого JSON массива.
     */
    @Test
    public void testConsumeEmptyMessage() throws JsonProcessingException {
        // Настройка моков
        when(objectMapper.readValue(eq("[]"), any(TypeReference.class))).thenReturn(new ArrayList<>());

        // Вызов тестируемого метода
        consumer.consumeMessage("[]");

        // Проверка, что методы моков были вызваны с правильными аргументами
        verify(objectMapper, times(1)).readValue(eq("[]"), any(TypeReference.class));
        verify(productService, never()).saveOrUpdateProduct(any(Products.class));
    }

    /**
     * Тест обработки исключения при десериализации.
     */
    @Test
    public void testConsumeMessageWithJsonProcessingException() throws JsonProcessingException {
        // Настройка моков
        when(objectMapper.readValue(eq("invalid json"), any(TypeReference.class)))
                .thenThrow(new JsonProcessingException("Test exception") {});

        // Проверка, что метод выбрасывает исключение
        assertThrows(JsonProcessingException.class, () -> {
            consumer.consumeMessage("invalid json");
        });

        // Проверка, что метод сохранения не был вызван
        verify(productService, never()).saveOrUpdateProduct(any(Products.class));
    }
}
