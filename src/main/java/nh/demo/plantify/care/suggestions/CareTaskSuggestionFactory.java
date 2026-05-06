package nh.demo.plantify.care.suggestions;

import nh.demo.plantify.shared.PlantType;

import java.util.List;

public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}

