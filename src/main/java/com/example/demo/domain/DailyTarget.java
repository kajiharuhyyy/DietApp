package com.example.demo.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "daily_targets",
       uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "target_date"}))
public class DailyTarget {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="target_date", nullable=false)
    private LocalDate date;

    @ManyToOne(optional=false)
    @JoinColumn(name="profile_id")
    private Profile profile;

    // 目標（単位はコメント参照）
    private Integer energyKcal;   // kcal
    private Double carbG;         // g
    private Double proteinG;      // g
    private Double fatG;          // g
    private Double fiberG;        // g
    private Double sodiumG;       // g（食塩相当量）

    private Double potassiumMg;   // mg（任意）
    private Double phosphorusMg;  // mg（任意）
}
