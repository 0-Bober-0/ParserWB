package ru.sasha.parser.send;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sasha.parser.models.Product;
import ru.sasha.parser.services.producer.Producer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса FileDataWriter.
 */
public class FileDataWriterTest {

    @Mock
    private Producer producer;

    @InjectMocks
    private DataSend fileDataWriter;

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем тестовые продукты
        products = Arrays.asList(
                new Product(1, "Мокасины кожаные", "ROOMAN", 1718, 4.8,
                        "https://www.wildberries.ru/catalog/241024982/detail.aspx"),
                new Product(2, "Кроссовки спортивные", "ADIDAS", 425, 4.5,
                        "https://www.wildberries.ru/catalog/12345/detail.aspx")
        );
    }

    /**
     * Тест успешной записи продуктов.
     */
    @Test
    public void testWriteProductsSuccess() throws JsonProcessingException {
        // Вызываем тестируемый метод
        fileDataWriter.writeProducts(products);

        // Проверяем, что метод Producer.sendMessage был вызван с правильными параметрами
        verify(producer).sendMessage(products);
    }

    /**
     * Тест обработки исключения при отправке сообщения.
     */
    @Test
    public void testWriteProductsWithJsonProcessingException() throws JsonProcessingException {
        // Настраиваем поведение producer для выброса исключения
        doThrow(new JsonProcessingException("JSON error") {})
                .when(producer).sendMessage(products);

        // Проверяем, что метод выбрасывает исключение
        assertThrows(JsonProcessingException.class, () -> fileDataWriter.writeProducts(products));
    }
}
