package com.scaler.NovModuleProject.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private Long id;
    private String title;

    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Category() {
    }
}
