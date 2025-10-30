package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.domain.DietType;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diet")
@SessionAttributes({"dietTypeSelected", "profileId"})
@RequiredArgsConstructor
public class DietController {


    @ModelAttribute("dietTypeSelected")
    public DietType initDietType() {
        return null;
    }

    @ModelAttribute("profileId")
    public Long initProfileId() {
        return null;
    }

    /*
     * モード選択ページ
     */
    @GetMapping("/select")
    public String select(){
        return "diet/select";
    }    

    /**
     * 糖尿病食モードに決定 → ダッシュボードへ
     * @param model
     * @return 糖尿病食ダッシュボード
     */    
    @PostMapping("/lowCarb")
    public String selectLowCarb(Model model) {
        model.addAttribute("dietTypeSelected", DietType.LOW_CARB);
        return "redirect:/diet/profile";
    }

    /**
     * CKD食モードに決定 → ダッシュボードへ
     * @param model
     * @return CKD食ダッシュボード
     */
    @PostMapping("/lowProtein")
    public String selecLowProtein(Model model) {
        model.addAttribute("dietTypeSelected", DietType.LOW_PROTEIN );
        return "redirect:/diet/profile";
    }

    /**
     * 糖尿病食モードのトップ（ダッシュボード予定地）
     * @param dietType
     * @param model
     * @return 糖尿病食ダッシュボード
     */
    @GetMapping("/diabetes")
    public String diabetesTop(@SessionAttribute(value = "dietTypeSelected", required = false) DietType dietType, Model model) {
        if (dietType != DietType.LOW_CARB) {
            return "redirect:/diet/select";
        }
        model.addAttribute("pageTitle", "糖尿病食モード");
        return "diet/diabetes";
    }

    /**
     * CKD食モードのトップ（ダッシュボード予定地）
     * @param dietType
     * @param model
     * @return CKD食ダッシュボード
     */
    @GetMapping("/ckd")
    public String ckdTop(@SessionAttribute(value = "dietTypeSelected", required = false) DietType dietType, Model model) {
        if (dietType != DietType.LOW_PROTEIN) {
            return "redirect:/diet/select";
        }
        model.addAttribute("pageTitle", "CKD食モード");
        return "diet/ckd";
    }
}
