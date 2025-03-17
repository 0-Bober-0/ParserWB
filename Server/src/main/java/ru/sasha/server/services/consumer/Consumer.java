package ru.sasha.server.services.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sasha.server.models.Products;
import ru.sasha.server.services.ProductService;


/**
 * Потребитель сообщений Kafka.
 * Принимает и обрабатывает сообщения из указанного топика Kafka.
 *
 * @author Alexandr Daev
 */
@Slf4j                  // Автоматически создает поле logger через Lombok
@Component              // Регистрирует класс как компонент Spring
public class Consumer {

    // Имя топика, из которого получаем сообщения
    @Value("${topic.name_n}")
    private String orderTopic;

    // Преобразование между JSON и объектами Java
    private final ObjectMapper objectMapper;

    // Для преобразования между различными типами объектов
    private final ModelMapper modelMapper;

    // Сервис для работы с продуктами
    private final ProductService productService;

    /**
     * Конструктор с автоматическим внедрением зависимостей.
     *
     * @param objectMapper Маппер для работы с JSON
     * @param modelMapper Маппер для преобразования объектов
     * @param productService Сервис для работы с продуктами
     */
    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, ProductService productService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    /**
     * Обрабатывает входящие сообщения из топик Kafka.
     * Метод вызывается автоматически при появлении нового сообщения.
     *
     * @param message Полученное сообщение в формате JSON
     * @throws JsonProcessingException Если возникает ошибка при разборе JSON
     */
    @KafkaListener(topics = "${topic.name_n}")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("Получено сообщение: {}", message);

        // Десериализация JSON-массива в список объектов Products
        List<Products> productsList = objectMapper.readValue(message,
                new TypeReference<List<Products>>() {});

        for (Products product : productsList) {
            log.debug("Обработка продукта: {}", product);
            productService.saveOrUpdateProduct(product);
        }

        log.info("Обработано {} продуктов", productsList.size());
    }
}
