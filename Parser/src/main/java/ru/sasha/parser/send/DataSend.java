package ru.sasha.parser.send;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sasha.parser.core.DataWriter;
import ru.sasha.parser.models.Product;
import ru.sasha.parser.services.producer.Producer;


/**
 * Записывает данные продуктов в указанный файл или отправляет в Kafka.
 *
 * @author Alexandr Daev
 */
@Slf4j
@Service
public class DataSend implements DataWriter {
    private final Producer producer;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param producer продюсер для отправки сообщений в Kafka
     */
    @Autowired
    public DataSend(Producer producer) {
        this.producer = producer;
    }

    /**
     * Отправляет список товаров.
     *
     * @param products список продуктов
     * @throws JsonProcessingException если происходит ошибка при обработке JSON
     */
    @Override
    public void writeProducts(List<Product> products) throws JsonProcessingException {
        log.info("Начало записи продуктов в файл.");
        producer.sendMessage(products);
    }
}
