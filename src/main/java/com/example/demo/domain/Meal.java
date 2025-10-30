package com.example.demo.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "meals")
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Profile profile;

    @Column(nullable = false)
    private LocalDate eatDate;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Integer grams;

    private Integer energyKcal;
    private Double proteinG;
    private Double carbG;
    private Double fatG;
    private Double sodiumG;
    private Double potassiumMg;
    private Double phosphorusMg;
    private Double fiberG;
}
