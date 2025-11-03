package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "foods", indexes = {
    @Index(name = "idx_foods_name", columnList = "name")
})
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @NotNull private Integer energyKcalPer100g;  // kcal
    @NotNull private Double proteinGPer100g;     // g
    @NotNull private Double fatGPer100g;         // g
    @NotNull private Double carbGPer100g;        // g
    @NotNull private Double fiberGPer100g;       // g
    @NotNull private Double sodiumGPer100g;      // g（食塩相当量）
    @NotNull private Double potassiumMgPer100g;  // mg
    @NotNull private Double phosphorusMgPer100g; // mg

}
