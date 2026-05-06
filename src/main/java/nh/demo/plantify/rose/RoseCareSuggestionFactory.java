package nh.demo.plantify.rose;

import nh.demo.plantify.care.CareTaskService;
import nh.demo.plantify.care.suggestions.CareTaskSuggestion;
import nh.demo.plantify.care.suggestions.CareTaskSuggestionFactory;
import nh.demo.plantify.shared.PlantType;
import org.springframework.stereotype.Component;

import java.util.List;

// Package private, keine öffentliche Komponente
@Component
class RoseCareSuggestionFactory implements CareTaskSuggestionFactory {
//                                  ^--- IntelliJ Bug, wird weiterhin als Fehler angezeigt, Test ist grün
//                                                    ^--- Problem: internes API
    
//    private CareTaskService careTaskService;
//                      ^--- Korrekt: CTS ist zwar public,
//                           aber nicht Bestandteil des NamedInterfaces
//                           das wir als einzige erlaubte Abhängigkeit
//                           eingetragen haben --> Test!


    @Override
    public List<CareTaskSuggestion> createSuggestion(PlantType plantType, String location) {
//                      ^--- IntelliJ Bug, wird weiterhin als Fehler angezeigt, Test ist grün
//                      ^--- Problem: internes API

        return List.of();
    }
}
