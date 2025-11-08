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

    @NotNull @Column(name = "energy_kcal_per100g")
    private Integer energyKcalPer100g;

    @NotNull @Column(name = "protein_g_per100g")
    private Double proteinGPer100g;

    @NotNull @Column(name = "fat_g_per100g")
    private Double fatGPer100g;

    @NotNull @Column(name = "carb_g_per100g")
    private Double carbGPer100g;

    @NotNull @Column(name = "fiber_g_per100g")
    private Double fiberGPer100g;

    @NotNull @Column(name = "sodium_g_per100g")
    private Double sodiumGPer100g;

    @NotNull @Column(name = "potassium_mg_per100g")
    private Double potassiumMgPer100g;

    @NotNull @Column(name = "phosphorus_mg_per100g")
    private Double phosphorusMgPer100g;

}
