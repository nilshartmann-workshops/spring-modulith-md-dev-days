package nh.demo.plantify.rose;

import nh.demo.plantify.care.suggestions.CareTaskSuggestion;
import nh.demo.plantify.care.suggestions.CareTaskSuggestionFactory;
import nh.demo.plantify.shared.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

// Package private, keine öffentliche Komponente
@Component
class RoseCareSuggestionFactory implements CareTaskSuggestionFactory {
//                                                    ^--- Problem: internes API
    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
//                      ^--- Problem: internes API

        return List.of();
    }
}
