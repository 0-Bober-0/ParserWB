package ru.sasha.parser.services.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.sasha.parser.models.Product;

/**
 * Класс для отправки данных в Kafka.
 *
 * @author Alexandr Daev
 */
@Slf4j
@Component
public class Producer {

    @Value("${topic.name_n}")
    private String orderTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param objectMapper    объект для работы с JSON
     * @param kafkaTemplate    KafkaTemplate для отправки сообщений
     */
    @Autowired
    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Отправляет сообщение в Kafka.
     *
     * @param products список продуктов для отправки
     * @throws JsonProcessingException если происходит ошибка при обработке JSON
     */
    public void sendMessage(List<Product> products) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(products);
        kafkaTemplate.send(orderTopic, message);
        log.info("Сообщение отправлено в топик {}: {}", orderTopic, message);
    }
}
