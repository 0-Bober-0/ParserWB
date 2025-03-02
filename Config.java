package ru.sasha.parser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Конфигурация Spring для настроек Kafka и других бинов приложения.
 * @author Daev Alexandr
 */
@EnableScheduling
@Configuration
public class Config {

    private final KafkaProperties kafkaProperties;

    /**
     * Конструктор, используемый для внедрения зависимостей.
     *
     * @param kafkaProperties настройки Kafka
     */
    @Autowired
    public Config(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * Создает фабрику Producers для отправки сообщений в Kafka.
     *
     * @return ProducerFactory для Kafka
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    /**
     * Создает KafkaTemplate для отправки сообщений.
     *
     * @return KafkaTemplate для отправки сообщений в Kafka
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Создает новый топик для Kafka с заданными параметрами.
     *
     * @return объект NewTopic для Kafka
     */
    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name("t.food.order")
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Создает объект ObjectMapper для работы с JSON.
     *
     * @return ObjectMapper для обработки JSON
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Создает объект ModelMapper для преобразования данных.
     *
     * @return ModelMapper для преобразования данных между типами
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
