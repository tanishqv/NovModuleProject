package com.scaler.NovModuleProject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category extends BaseModel{
    private String title;
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude List<Product> products;
}
