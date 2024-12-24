package com.scaler.NovModuleProject.projections;

import com.scaler.NovModuleProject.models.Category;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    Double getPrice();
    String getImageUrl();
    Category getCategory();
}
