package com.example.demo.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.DailyTarget;
import com.example.demo.domain.Meal;
import com.example.demo.domain.Profile;
import com.example.demo.repo.MealRepository;
import com.example.demo.repo.ProfileRepository;
import com.example.demo.service.FoodCatalog;
import com.example.demo.service.MealService;
import com.example.demo.service.TargetCalculator;
import com.example.demo.service.MealService.Totals;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diet/meal")
@SessionAttributes({"profileId", "dietTypeSelected"})
@RequiredArgsConstructor
public class MealController {

    private final ProfileRepository profileRepo;
    private final MealService mealService;
    private final TargetCalculator targetCalculator;
    private final MealRepository mealRepo;

    @ModelAttribute("mealForm")
    public MealForm mealForm() {
        return new MealForm();
    }

    @GetMapping
    public String show(@SessionAttribute(value = "profileId", required = false) Long profileId, Model model) {
        if (profileId == null) return "redirect:/diet/selest";

                Profile p = profileRepo.findById(profileId).orElse(null);
                if (p == null) return "redirect:/diet/select";

                LocalDate today = LocalDate.now();
                List<Meal> meals = mealService.listToday(profileId, today);
                Totals totals = mealService.sum(meals);
                DailyTarget target = targetCalculator.calculateAndPersist(p, today);

                model.addAttribute("foods", FoodCatalog.ITEMS.keySet()); // セレクトボックス用
                model.addAttribute("meals", meals);
                model.addAttribute("totals", totals);
                model.addAttribute("target", target);
                model.addAttribute("pageTitle", "食事入力");

        return "diet/meal";
    }

    @PostMapping
    public String add(@SessionAttribute(value = "profileId", required = false) Long profileId, @ModelAttribute("mealForm") MealForm form) {
        if(profileId == null) return "redirect:/diet/select";

        Profile p = profileRepo.findById(profileId).orElse(null);
        if(p == null) return "redirect:/diet/select";
        
        mealService.addMeal(p, LocalDate.now(), form.getFoodName(), form.getGrams());
        return "redirect:/diet/meal";
    }

    @PostMapping("/diet/meal/{mealId}/delete")
    public String deleteMeal(
            @PathVariable Long mealId,
            @SessionAttribute(value = "profileId", required = false) Long profileId,
            RedirectAttributes ra) {

                if (profileId == null) {
                    return "redirect:/diet/select";
                }

                mealRepo.findByIdAndProfileId(mealId, profileId)
                    .ifPresent(m -> mealRepo.deleteById(mealId));

                ra.addFlashAttribute("msg", "食事を削除しました。");
                return "redirect:/diet/meal";
            }

    public static class MealForm {
        private String foodName;
        private int grams = 100;

        public String getFoodName() {
            return foodName;
        }
        public void setFoodName(String s) {
            this.foodName = s;
        }
        public int getGrams() {
            return grams;
        }
        public void setGrams(int g) {
            this.grams = g;
        }
    }
}
