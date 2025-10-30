package com.example.demo.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.domain.DailyTarget;
import com.example.demo.domain.DietType;
import com.example.demo.domain.Profile;
import com.example.demo.repo.ProfileRepository;
import com.example.demo.service.TargetCalculator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/diet")
@SessionAttributes({"dietTypeSelected", "profileId"})
public class ProfileController {

    private final ProfileRepository profileRepo;
    private final ProfileMapper mapper;
    private final TargetCalculator calculator;

    @ModelAttribute("dietTypeSelected")
    public DietType dietTypeInit() { return null; }

    @ModelAttribute("profileId")
    public Long profileIdInit() { return null; }

    /** プロファイル入力フォーム表示 */
    @GetMapping("/profile")
    public String profileForm(@SessionAttribute(value = "dietTypeSelected", required = false) DietType dietType, Model model) {
        if (dietType == null) return "redirect:/diet/select";
        ProfileForm form = new ProfileForm();
        form.setDietType(dietType);
        form.setActivityFactor(1.5); // デフォルト例
        model.addAttribute("form", form);
        return "diet/profile";
    }

    /** プロファイル登録 → 目標計算 → ダッシュボード */
    @PostMapping("/profile")
    public String submitProfile(@Valid @ModelAttribute("form") ProfileForm form,
                                BindingResult br,
                                Model model) {
        if (br.hasErrors()) return "diet/profile";

        Profile p = profileRepo.save(mapper.toEntity(form));
        model.addAttribute("profileId", p.getId());
        model.addAttribute("dietTypeSelected", p.getDietType());

        DailyTarget t = calculator.calculateAndPersist(p, LocalDate.now());

        // ダッシュボードへ
        return "redirect:/diet/dashboard";
    }

    /** ダッシュボード表示 */
    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute(value = "profileId", required=false) Long profileId,
                            @SessionAttribute(value="dietTypeSelected", required=false) DietType dietType,
                            Model model) {
        if (profileId == null || dietType == null) return "redirect:/diet/select";
        // 最低限の表示用（当日目標だけ）
        Profile p = profileRepo.findById(profileId).orElse(null);
        DailyTarget target = (p == null) ? null : calculator.calculateAndPersist(p, LocalDate.now());

        model.addAttribute("profile", p);
        model.addAttribute("target", target);

        if(dietType == DietType.LOW_PROTEIN) {
            model.addAttribute("pageTitle", "低たんぱく食ダッシュボード");
            return "diet/ckd";
        } else {
            model.addAttribute("pageTitle", "糖尿病食ダッシュボード");
            return "diet/diabetes";
        }
    
    }

    @GetMapping("/reset")
    public String reset(SessionStatus status) {
        status.setComplete();
        return "redirect:/diet/select";
    }
}

