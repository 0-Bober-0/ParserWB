package ru.sasha.server.services.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.sasha.server.models.Products;

/**
 * Производитель сообщений Kafka.
 * Отправляет сообщения в указанный топик Kafka.
 *
 * @author Alexandr Daev
 */
@Slf4j // Автоматически создает поле logger через Lombok
@Component // Регистрирует класс как компонент Spring
public class Producer {

    // Имя топика, в который отправляем сообщения
    @Value("${topic.name}")
    private String orderTopic;

    // Преобразование между JSON и объектами Java
    private final ObjectMapper objectMapper;

    // Шаблон для отправки сообщений в Kafka
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Конструктор с автоматическим внедрением зависимостей.
     *
     * @param kafkaTemplate Шаблон для работы с Kafka
     * @param objectMapper Маппер для работы с JSON
     */
    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Отправляет продукт в виде JSON-сообщения в Kafka.
     *
     * @param products Продукт для отправки
     * @throws JsonProcessingException Если возникает ошибка при сериализации в JSON
     */
    public void sendMessage(Products products) throws JsonProcessingException {
        // Преобразуем объект в JSON-строку
        String orderAsMessage = objectMapper.writeValueAsString(products);

        // Логируем отправку сообщения
        log.info("Отправка сообщения в топик {}: {}", orderTopic, orderAsMessage);

        // Отправляем сообщение в Kafka
        kafkaTemplate.send(orderTopic, orderAsMessage);

        log.debug("Сообщение успешно отправлено");
    }
}
