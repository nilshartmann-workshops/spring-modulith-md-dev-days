package nh.demo.plantify.rose;

import nh.demo.plantify.care.suggestion.CareTaskSuggestion;
import nh.demo.plantify.care.suggestion.CareTaskSuggestionFactory;
import nh.demo.plantify.shared.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoseCareTaskSuggestionFactory implements CareTaskSuggestionFactory {
    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
        return List.of();
    }
}
