package nh.demo.plantify.care.suggestion;

import nh.demo.plantify.shared.PlantType;
import org.springframework.modulith.NamedInterface;

import java.util.List;

@NamedInterface(name = "Care Task Suggestions")
public interface CareTaskSuggestionFactory {

    List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location);

}

