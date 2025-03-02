package ru.sasha.server.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sasha.server.models.Products;


/**
 * Репозиторий для выполнения операций с данными продуктов в базе данных.
 * Наследует JpaRepository для использования встроенных методов CRUD.
 *
 * @author Alexandr Daev
 */
@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    Optional<Products> findByTitleAndBrandAndUrl(String title, String brand, String url);
}
