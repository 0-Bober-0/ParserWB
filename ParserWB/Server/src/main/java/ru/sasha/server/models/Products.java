package ru.sasha.server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель продукта для хранения и обработки данных.
 *
 * @author Alexandr Daev
 */
@Entity                      // Сущность JPA для хранения в базе данных
@Getter                      // Автогенерация геттеров через Lombok
@Setter                      // Автогенерация сеттеров через Lombok
@Table(name = "Products")    // Имя таблицы в базе данных
@AllArgsConstructor          // Lombok: конструктор со всеми аргументами
@NoArgsConstructor           // Lombok: конструктор без аргументов
public final class Products {

    @Id                       // Первичный ключ
    @Column(name = "id")      // Имя столбца в таблице
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоинкремент
    private int id;

    /**
     * Название продукта.
     */
    @Column(name = "title")
    private String title;

    /**
     * Бренд продукта.
     */
    @Column(name = "brand")
    private String brand;

    /**
     * Общее количество отзывов.
     */
    @Column(name = "total_reviews")
    private int totalReviews;

    /**
     * Средняя оценка продукта.
     */
    @Column(name = "average_score")
    private double averageScore;

    /**
     * URL-адрес продукта на сайте.
     */
    @Column(name = "url")
    private String url;

    /**
     * Переопределение метода toString для представления объекта в JSON-формате.
     *
     * @return Строковое представление объекта в формате JSON
     */
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public String toString() {
        return "{"
                + "\"title\": \"" + title + "\","
                + " \"brand\": \"" + brand + "\","
                + " \"total_reviews\": " + totalReviews + ","
                + " \"average_score\": " + averageScore + ","
                + " \"url\": \"" + url + "\""
                + "}";
    }
}
