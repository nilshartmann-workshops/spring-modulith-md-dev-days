package nh.demo.plantify.plant;

import nh.demo.plantify.billing.UsageTracker;
import nh.demo.plantify.care.CareTaskService;
import nh.demo.plantify.shared.PlantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
class PlantService {

    private static final Logger log = LoggerFactory.getLogger(PlantService.class);

    private final PlantRepository plantRepository;
//    private final CareTaskService careTaskService;
//    private final UsageTracker usageTracker;
    private final ApplicationEventPublisher applicationEventPublisher;

    PlantService(PlantRepository plantRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.plantRepository = plantRepository;
//        this.careTaskService = careTaskService;
//        this.usageTracker = usageTracker;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {

        // Pflanze in DB speichern
        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);

        // Ausführen: ./add-plant.sh
        //
        // Hier haben wir zwei Aufrufe von anderen Services
        //
        //  🤔 Was sind die Konsequenzen?

        // Zeigen:
        //  -> Unterschiedliche Threads bei der Verarbeitung
        //  -> Asynchron (Folgeverarbeitung blockiert Haupt Use-Case nicht)
        applicationEventPublisher.publishEvent(
            new PlantRegisteredEvent(
                plant.getId(),
                plant.getOwnerId(),
                plant.getPlantType(),
                plant.getLocation()
            )
        );


//        // Care-Tasks anlegen
//        careTaskService.setupInitialCareTasks(
//            plant.getId(),
//            plant.getPlantType(),
//            plant.getLocation()
//        );
//
//        // Einrichtungsgebühr berechnen
//        usageTracker.registerSetupFee(
//            plant.getId(),
//            plant.getOwnerId()
//        );

        log.info("""
            
            
            ✅
            ✅ New plant registered '{}'
            ✅
            
            """, plant.getId());

        return plant;
    }

    public Optional<UUID> findOwnerForPlant(UUID plantId) {
        return plantRepository
            .findById(plantId)
            .map(Plant::getOwnerId)
            ;
    }

}