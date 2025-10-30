package com.example.demo.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Meal;

public interface MealRepository extends JpaRepository<Meal, Long>{
    List<Meal> findByProfileIdAndEatDateOrderByIdAsc(Long profileId, LocalDate eatDate);

}
