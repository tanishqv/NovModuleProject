package com.scaler.NovModuleProject.projections;

import com.scaler.NovModuleProject.models.Category;
import jakarta.persistence.ManyToOne;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    Double getPrice();
    String getImageUrl();
    Category getCategory();
}
