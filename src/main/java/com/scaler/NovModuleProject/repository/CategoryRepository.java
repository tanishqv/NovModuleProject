package com.scaler.NovModuleProject.repository;

import com.scaler.NovModuleProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Save/Update
    Category save(Category category);

    // get
    Optional<Category> findByTitle(String title);

    // delete
    void deleteById(Long id);
}
