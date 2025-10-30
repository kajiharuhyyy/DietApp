package com.example.demo.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.DailyTarget;
import com.example.demo.domain.DietType;
import com.example.demo.domain.Profile;
import com.example.demo.repo.DailyTargetRepository;
import com.example.demo.service.TargetCalculator;

import lombok.Data;

@Data
@Service
public class TargetCalculatorImpl implements TargetCalculator{

    private final DailyTargetRepository targetRepo;

    @Override
    @Transactional
    public DailyTarget calculateAndPersist(Profile p, LocalDate date) {
        // 既にあるなら上書き（スナップショット更新）
        DailyTarget t = targetRepo.findByProfileAndDate(p, date)
                .orElseGet(DailyTarget::new);
        t.setProfile(p);
        t.setDate(date);

        // 総エネルギー
        int energy = (p.getOverrideEnergyKcal() != null)
                ? p.getOverrideEnergyKcal()
                : estimateEnergyByMifflin(p); // BMR×活動係数 の簡易版
        t.setEnergyKcal(energy);

        if (p.getDietType() == DietType.LOW_CARB) {
            // --- 糖尿病食（炭水化物中心）
            double carbRatio = 0.50;      // 初期：50%
            double fatRatio  = 0.25;      // 25%
            double protRatio = 0.25;      // 25%（後で調整OK）

            double carbG = (energy * carbRatio) / 4.0;
            double fatG  = (energy * fatRatio ) / 9.0;
            double protG = (energy * protRatio) / 4.0;

            t.setCarbG(round1(carbG));
            t.setFatG(round1(fatG));
            t.setProteinG(round1(protG));

            // 食物繊維（暫定：男21g/女18g）
            double fiber = "male".equals(p.getSex()) ? 21.0 : 18.0;
            t.setFiberG(fiber);

            // 食塩相当量：一般推奨ベースで 6.0 g/day（任意）
            t.setSodiumG(6.0);

        } else {
            // --- 低たんぱく食（CKD非透析想定）
            double proteinPerKg = 0.8;     // 0.6〜0.8 g/kg/day（初期0.8）
            double kcalPerKg    = 30.0;    // 30〜35 kcal/kg/day（初期30）

            double protG = p.getWeightKg() * proteinPerKg;
            int energyCkd = (p.getOverrideEnergyKcal() != null)
                    ? p.getOverrideEnergyKcal()
                    : (int)Math.round(p.getWeightKg() * kcalPerKg);

            t.setEnergyKcal(energyCkd);
            t.setProteinG(round1(protG));

            // 残りを脂質・炭水化物に配分（脂質 30% として計算例）
            double fatRatio = 0.30;
            double fatG  = (energyCkd * fatRatio) / 9.0;
            t.setFatG(round1(fatG));

            // 炭水化物は残差から
            double carbKcal = energyCkd - (fatG*9.0 + protG*4.0);
            t.setCarbG(round1(carbKcal / 4.0));

            // 食塩相当量 初期6g/day
            t.setSodiumG(6.0);

            // 参考：しきい値を持つ場合はここでK/Pも
            // t.setPotassiumMg(...); t.setPhosphorusMg(...);
        }

        return targetRepo.save(t);
    }

    private int estimateEnergyByMifflin(Profile p) {
        // Mifflin-St Jeor（簡易）
        double weight = p.getWeightKg();
        double height = p.getHeightCm();
        int age = p.getAge();

        double bmr;
        if ("male".equals(p.getSex())) {
            bmr = 10*weight + 6.25*height - 5*age + 5;
        } else {
            bmr = 10*weight + 6.25*height - 5*age - 161;
        }
        return (int)Math.round(bmr * p.getActivityFactor());
    }

    private double round1(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}
