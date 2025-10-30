package com.example.demo.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.DailyTarget;
import com.example.demo.domain.Profile;

public interface DailyTargetRepository extends JpaRepository<DailyTarget, Long> {
    Optional<DailyTarget> findByProfileAndDate(Profile profile, LocalDate date);
}
