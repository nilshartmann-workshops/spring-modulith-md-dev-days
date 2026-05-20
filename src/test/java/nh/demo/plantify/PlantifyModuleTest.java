package nh.demo.plantify;


import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class PlantifyModuleTest {

    @Test
    void write_document() {

        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        new Documenter(modules)
            .writeDocumentation();
    }

    @Test
    void verify_modules() {
        var modules = ApplicationModules
            .of(PlantifyApplication.class);

        modules.verify();
    }

 }
