package com.scaler.NovModuleProject.repository;

import com.scaler.NovModuleProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Save/Update
    Product save(Product product);

    // Get
    Optional<Product> findById(Long id);

    // HQL
    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> findProductByCategoryId(@Param("categoryId") Long categoryId);

    // Native Query
    @Query(value="select * from product p where p.category_id = :categoryId", nativeQuery = true)
    List<Product> findProductByCategoryIdUsingNativeQuery(@Param("categoryId") Long categoryId);

    // Get all
    List<Product> findAll();

    // Delete
    void deleteById(Long id);
}
