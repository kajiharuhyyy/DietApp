package com.example.demo.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class FoodCatalog {

    public static record Nutri100(Integer kcal, double protein, double carb, double fat,
     double sodiumG, double potassiumMg, double phosphorusMg, double fiber) {
    }

    public static final Map<String, Nutri100> ITEMS = new LinkedHashMap<>();
    static {
        ITEMS.put("ご飯(白米)", new Nutri100(168, 2.5, 37.1, 0.3, 0.0, 29, 43, 0.3));
        ITEMS.put("鶏むね(皮なし)", new Nutri100(116, 23.3, 0.0, 1.9, 0.2, 350, 200, 0.0));
        ITEMS.put("木綿豆腐", new Nutri100(72, 6.6, 1.7, 4.2, 0.0, 150, 120, 0.4));
        ITEMS.put("ブロッコリー", new Nutri100(33, 4.3, 5.2, 0.5, 0.0, 360, 66, 4.4));
        ITEMS.put("食パン", new Nutri100(264, 9.3, 46.7, 4.1, 1.3, 110, 100, 2.3));
    }
}
