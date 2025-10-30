package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "profiles")
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Enumerated(EnumType.STRING)
    private DietType dietType;

    @NotNull @Min(100) @Max(250)
    private Integer heightCm;

    @NotNull @Min(20) @Max(250)
    private Double weightKg;

    @NotNull @Min(10) @Max(100)
    private Integer age;

    @NotNull
    @Pattern(regexp = "male|female")
    private String sex; // male/female

    // 活動係数（1.2〜1.9あたり）
    @NotNull @DecimalMin("1.1") @DecimalMax("2.5")
    private Double activityFactor;

    // 任意：ユーザーが総エネルギーを直接固定したい時（nullなら推定式）
    private Integer overrideEnergyKcal;

    // CKD段階（LOW_PROTEIN時に使う、null可）
    private Integer ckdStage; // 1..5
}
