package ru.sasha.server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sasha.server.models.Products;
import ru.sasha.server.repositories.ProductsRepository;
import ru.sasha.server.services.producer.Producer;


/**
 * Сервис для работы с продуктами.
 * Реализует бизнес-логику приложения для управления продуктами.
 *
 * @author Alexandr Daev
 */
@Service                      // Регистрирует класс как сервис Spring
@Transactional(readOnly = true)  // По умолчанию все методы только для чтения
public class ProductService {

    // Репозиторий для работы с данными продуктов
    private final ProductsRepository productRepository;

    // Производитель сообщений Kafka
    private final Producer producer;

    /**
     * Конструктор с автоматическим внедрением зависимостей.
     *
     * @param productRepository Репозиторий для работы с продуктами
     * @param producer Производитель сообщений для Kafka
     */
    @Autowired
    public ProductService(ProductsRepository productRepository, Producer producer) throws JsonProcessingException {
        this.productRepository = productRepository;
        this.producer = producer;
    }

    /**
     * Сохраняет продукт в базе данных.
     *
     * @param product Продукт для сохранения
     */
    @Transactional
    public void saveOrUpdateProduct(Products product) {
        Optional<Products> existingProduct = productRepository.findByTitleAndBrandAndUrl(
                product.getTitle(),
                product.getBrand(),
                product.getUrl()
        );

        if (existingProduct.isPresent()) {
            Products updatedProduct = existingProduct.get();
            updatedProduct.setTotalReviews(product.getTotalReviews());
            updatedProduct.setAverageScore(product.getAverageScore());
            productRepository.save(updatedProduct);
        } else {
            productRepository.save(product);
        }
    }


    /**
     * Создает заказ на продукт путем отправки сообщения в Kafka.
     *
     * @param products Продукт для создания заказа
     * @throws JsonProcessingException Если возникает ошибка при обработке JSON
     */
    public void create(Products products) throws JsonProcessingException {
        producer.sendMessage(products);
    }
}
