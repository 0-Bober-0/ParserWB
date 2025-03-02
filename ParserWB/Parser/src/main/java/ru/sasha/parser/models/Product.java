package ru.sasha.parser.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель продукта.
 *
 * @author Alexandr Daev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String title;
    private String brand;
    private int totalReviews;
    private double averageScore;
    private String url;

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
