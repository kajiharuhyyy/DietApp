package com.example.demo.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Meal;

public interface MealRepository extends JpaRepository<Meal, Long>{
    List<Meal> findByProfileIdAndEatDateOrderByIdAsc(Long profileId, LocalDate eatDate);
    Optional<Meal> findByIdAndProfileId(Long id, Long profileId);
    void deleteByIdAndProfileId(Long id, Long profileId);
}
