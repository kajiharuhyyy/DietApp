package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Meal;
import com.example.demo.domain.Profile;
import com.example.demo.repo.MealRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepo;

    public Meal addMeal(Profile profile, LocalDate date, String foodName, int grams) {
        var base = FoodCatalog.ITEMS.get(foodName);
        if(base == null) throw new IllegalArgumentException("Unknown food: " + foodName);

        double ratio = grams / 100.0;

        Meal m = new Meal();
        m.setProfile(profile);
        m.setEatDate(date);
        m.setFoodName(foodName);
        m.setGrams(grams);
        m.setEnergyKcal((int)Math.round(base.kcal() * ratio));
        m.setProteinG(round1(base.protein() * ratio));
        m.setCarbG(round1(base.carb() * ratio));
        m.setFatG(round1(base.fat() * ratio));
        m.setSodiumG(round2(base.sodiumG() * ratio));
        m.setPotassiumMg(round1(base.potassiumMg() * ratio));
        m.setPhosphorusMg(round1(base.phosphorusMg() * ratio));
        m.setFiberG(round1(base.fiber() * ratio));
        return mealRepo.save(m);
    }

    public List<Meal> listToday(Long profileId, LocalDate date) {
        return mealRepo.findByProfileIdAndEatDateOrderByIdAsc(profileId, date);
    }

    public Totals sum(List<Meal> meals) {
        Totals t = new Totals();
        for (Meal m : meals) {
            t.energyKcal += nz(m.getEnergyKcal());
            t.proteinG += nz(m.getProteinG());
            t.carbG += nz(m.getCarbG());
            t.fatG += nz(m.getFatG());
            t.sodiumG += nz(m.getSodiumG());
            t.potassiumMg += nz(m.getPotassiumMg());
            t.phosphorusMg += nz(m.getPhosphorusMg());
            t.fiberG += nz(m.getFiberG());
        }
        t.round();
        return t;
    }

    public static class Totals {
        public int energyKcal = 0;
        public double proteinG = 0, carbG = 0, fatG = 0, sodiumG = 0, potassiumMg = 0, phosphorusMg = 0, fiberG = 0;
        void round() {
            proteinG = round1(proteinG);
            carbG = round1(carbG);
            fatG = round1(fatG);
            sodiumG = round2(sodiumG);
            potassiumMg = round1(potassiumMg);
            phosphorusMg = round1(phosphorusMg);
            fiberG = round1(fiberG);
        }
    }

    private static double nz(Double v) {
        return v == null ? 0.0 : v;
    }
    private static int nz(Integer v) {
        return v == null ? 0 : v;
    }
    private static double round1(double v) {
        return Math.round(v * 10.0)/10.0;
    }
        private static double round2(double v) {
        return Math.round(v * 10.0)/10.0;
    }
}
