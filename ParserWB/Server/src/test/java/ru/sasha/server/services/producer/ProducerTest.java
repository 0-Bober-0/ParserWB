package ru.sasha.server.services.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import ru.sasha.server.models.Products;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Тесты для Producer (компонент отправки в Kafka).
 */
@ExtendWith(MockitoExtension.class)
public class ProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private Producer producer;

    private Products testProduct;
    private String testJson;

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

        testJson = "{\"id\":1,\"title\":\"Тестовый продукт\",\"brand\":\"Тестовый бренд\"," +
                "\"total_reviews\":100,\"average_score\":4.5," +
                "\"url\":\"https://www.wildberries.ru/catalog/12345/detail.aspx\"}";

        // Устанавливаем значение приватного поля через рефлексию (т.к. оно устанавливается через @Value)
        ReflectionTestUtils.setField(producer, "orderTopic", "t.food.order");
    }

    /**
     * Тест метода sendMessage().
     */
    @Test
    public void testSendMessage() throws JsonProcessingException {
        // Настройка моков
        when(objectMapper.writeValueAsString(testProduct)).thenReturn(testJson);
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(null);

        // Вызов тестируемого метода
        producer.sendMessage(testProduct);

        // Проверка, что методы моков были вызваны с правильными аргументами
        verify(objectMapper, times(1)).writeValueAsString(testProduct);
        verify(kafkaTemplate, times(1)).send(eq("t.food.order"), eq(testJson));
    }

    /**
     * Тест обработки исключения при сериализации.
     */
    @Test
    public void testSendMessageWithJsonProcessingException() throws JsonProcessingException {
        // Настройка моков
        when(objectMapper.writeValueAsString(testProduct)).thenThrow(new JsonProcessingException("Test exception") {});

        // Проверка, что метод выбрасывает исключение
        assertThrows(JsonProcessingException.class, () -> {
            producer.sendMessage(testProduct);
        });

        // Проверка, что метод отправки в Kafka не был вызван
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }
}
