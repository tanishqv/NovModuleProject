package com.scaler.NovModuleProject.repository;

import com.scaler.NovModuleProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Save/Update
    Product save(Product product);

    // Get
    Optional<Product> findById(Long id);
    Product findByTitle(String title);

    // Get all
    List<Product> findAll();

    // delete
    void deleteById(Long id);
}
