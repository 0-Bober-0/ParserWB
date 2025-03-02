package ru.sasha.server.config;

import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Конфигурационный класс приложения.
 * Содержит настройки Kafka и дополнительные компоненты для работы с данными.
 *
 * @author Alexandr Daev
 */
@Configuration        // Указывает, что класс содержит бины конфигурации Spring
public class Config {

    // Свойства Kafka для настройки подключения
    private final KafkaProperties kafkaProperties;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param kafkaProperties Свойства Kafka из автоконфигурации Spring
     */
    @Autowired
    public Config(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * Создает и настраивает фабрику производителя Kafka.
     *
     * @return Настроенная фабрика производителя для отправки сообщений в Kafka
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // Получаем настройки производителя из свойств Kafka
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        // Создаем фабрику для работы со строковыми ключами и значениями
        return new DefaultKafkaProducerFactory<>(properties);
    }

    /**
     * Создает шаблон Kafka для упрощенной отправки сообщений.
     *
     * @return Шаблон KafkaTemplate для отправки сообщений
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Создает экземпляр ModelMapper для преобразования объектов.
     *
     * @return Настроенный объект ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
