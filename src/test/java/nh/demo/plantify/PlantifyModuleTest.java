package nh.demo.plantify;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class PlantifyModuleTest {

    @Test
    void verify_modules() {
        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        modules.detectViolations()
            // Alternative, um bestehende Probleme zu ignorieren:
//            .filter(v -> !v.hasMessageContaining(" Method <nh.demo.plantify.care.CareTaskService.setupInitialCareTasks"))
            .throwIfPresent();

        // -> PlantType in 'shared' schieben
        //   -> andere Optionen:
        //    - ignorieren (wäre für Migration legitim)
        //    - jedes Modul hat eigene Repräsentation eines Pflanzentyps
        //    - wir bauen nachher die App sowieso nochmal um, dann kann PlantType zurück

    }

    @Test
    void write_document() {

        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        new Documenter(modules)
            .writeDocumentation();

        // -> Packges im Workspace kurz vorstellen
        // -> components.puml
        //    -> Abhängigkeiten zeigen
        //    -> was das bedeutet sehen wir später
        //    -> 🕵️‍♂️ Wir haben zirkuläre Abhängigkeiten
    }

}
