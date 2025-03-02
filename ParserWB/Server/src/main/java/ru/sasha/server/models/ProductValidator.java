package ru.sasha.server.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель url для хранения и валидации данных.
 *
 * @author Alexandr Daev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductValidator {
    @NotBlank(message = "URL не может быть пустым.")
    @Pattern(regexp = "^https://www\\.wildberries\\.ru/(catalog/.+|seller/\\d+)$",
            message = "Неправильный формат ссылки")
    private String urlForm;

    @Override
    public String toString() {
        return "ProductValidator{"
                + "url_form='" + urlForm + '\''
                + '}';
    }
}
