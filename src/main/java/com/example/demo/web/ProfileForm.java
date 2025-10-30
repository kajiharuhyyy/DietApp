package com.example.demo.web;

import com.example.demo.domain.DietType;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProfileForm {
    @NotNull
    private DietType dietType;

    @NotNull @Min(100) @Max(250)
    private Integer heightCm;

    @NotNull @Min(20) @Max(250)
    private Double weightKg;

    @NotNull @Min(10) @Max(100)
    private Integer age;

    @NotNull @Pattern(regexp = "male|female")
    private String sex;

    @NotNull @DecimalMin("1.1") @DecimalMax("2.5")
    private Double activityFactor;

    private Integer overrideEnergyKcal; // null可
    private Integer ckdStage;           // null可
}
