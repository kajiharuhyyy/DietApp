package com.example.demo.web;

import org.springframework.stereotype.Component;

import com.example.demo.domain.Profile;

@Component
public class ProfileMapper {
        public Profile toEntity(ProfileForm f) {
        Profile p = new Profile();
        p.setDietType(f.getDietType());
        p.setHeightCm(f.getHeightCm());
        p.setWeightKg(f.getWeightKg());
        p.setAge(f.getAge());
        p.setSex(f.getSex());
        p.setActivityFactor(f.getActivityFactor());
        p.setOverrideEnergyKcal(f.getOverrideEnergyKcal());
        p.setCkdStage(f.getCkdStage());
        return p;
    }
}
