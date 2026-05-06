package nh.demo.plantify.rose;

import nh.demo.plantify.care.CareTaskSuggestion;
import nh.demo.plantify.care.CareTaskSuggestionFactory;
import nh.demo.plantify.shared.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

// Package private, keine öffentliche Komponente
@Component
class RoseCareSuggestionFactory implements CareTaskSuggestionFactory {
    
    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
        return List.of();
    }
}
