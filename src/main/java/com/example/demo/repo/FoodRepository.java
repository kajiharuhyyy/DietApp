package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findTop50ByNameContainingIgnoreCaseOrderByNameAsc(String q);
    Food findByName(String name);
 
}
