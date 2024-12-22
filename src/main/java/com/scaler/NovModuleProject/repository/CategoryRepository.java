package com.scaler.NovModuleProject.repository;

import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.projections.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Save/Update
    Category save(Category category);

    // get
    Optional<Category> findByTitle(String title);

    @Query("select c.id as id, c.title as title from Category c")
    List<CategoryProjection> findAllUsingProjection();
}
