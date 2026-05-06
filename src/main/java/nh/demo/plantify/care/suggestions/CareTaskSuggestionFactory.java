package nh.demo.plantify.care.suggestions;

import nh.demo.plantify.shared.PlantType;
import org.springframework.modulith.NamedInterface;

import java.util.List;

@NamedInterface(name = "Suggestion Factory")
public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}

