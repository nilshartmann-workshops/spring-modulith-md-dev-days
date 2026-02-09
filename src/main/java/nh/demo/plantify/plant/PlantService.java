package nh.demo.plantify.plant;

import nh.demo.plantify.care.CareTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService {

    private static final Logger log = LoggerFactory.getLogger( PlantService.class );

    private final PlantRepository plantRepository;
    private final ApplicationEventPublisher events;
    private final CareTaskService careTaskService;

    PlantService(PlantRepository plantRepository, ApplicationEventPublisher events, CareTaskService careTaskService) {
        this.plantRepository = plantRepository;
        this.events = events;
        this.careTaskService = careTaskService;
    }

    @Transactional
    Plant registerPlant(UUID ownerId, String name, PlantType plantType, String location) {
        // Keine Duplikate für denselben Owner
        if (plantRepository.existsByOwnerIdAndName(ownerId, name)) {
            throw new IllegalArgumentException("Plant with name '%s' already exists for this owner".formatted(name));
        }

        var plant = new Plant(ownerId, name, plantType, location);
        plantRepository.save(plant);

        careTaskService.setupInitialCareTasks(
            plant.getId(),
            plant.getPlantType(),
            plant.getLocation()
        );

        return plant;
    }

    public Optional<UUID> findOwnerForPlant(UUID plantId) {
        return plantRepository
            .findById(plantId)
            .map(Plant::getOwnerId)
            ;
    }

}