package ru.sasha.parser.services.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sasha.parser.models.Product;
import ru.sasha.parser.services.ProductScraperService;

/**
 * Класс, который слушает сообщения из Kafka и обрабатывает их.
 *
 * @author Alexandr Daev
 */
@Slf4j
@Component
public class Consumer {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param objectMapper    объект для работы с JSON
     * @param modelMapper     объект для преобразования данных
     */
    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    /**
     * Метод, который срабатывает при получении сообщения из Kafka.
     *
     * @param message сообщение в формате JSON
     * @throws JsonProcessingException если происходит ошибка при обработке JSON
     */
    @KafkaListener(topics = "${topic.name}")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("Получено сообщение: {}", message);
        Product product = objectMapper.readValue(message, Product.class);
        ProductScraperService.runScraper(product.getUrl());
    }
}
