package com.scaler.NovModuleProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Category extends BaseModel {
    private String title;
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude @JsonIgnore List<Product> products;
}
