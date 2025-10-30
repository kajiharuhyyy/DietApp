package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.domain.DailyTarget;
import com.example.demo.domain.Profile;

public interface TargetCalculator {
    DailyTarget calculateAndPersist(Profile profile, LocalDate date);
}
