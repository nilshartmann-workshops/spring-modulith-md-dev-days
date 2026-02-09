package nh.demo.plantify.billing;

import nh.demo.plantify.billing.invoice.UsageRecord;
import nh.demo.plantify.billing.invoice.UsageRepository;
import nh.demo.plantify.billing.invoice.UsageType;
import nh.demo.plantify.care.suggestion.CareTaskType;
import nh.demo.plantify.plant.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UsageTracker {

    private static final Logger log = LoggerFactory.getLogger(UsageTracker.class);
    private final UsageRepository usageRepository;
    private final PlantService plantService;

    UsageTracker(UsageRepository usageRepository, PlantService plantService) {
        this.usageRepository = usageRepository;
        this.plantService = plantService;
    }

    @Transactional
    public void initialCareTasksCreated(UUID plantId) {
        UsageRecord usageRecord = new UsageRecord(
            getOwnerForPlant(plantId),
            UsageType.SETUP_FEE,
            Instant.now(),
            1000L
        );

        usageRepository.save(usageRecord);
    }

    private UUID getOwnerForPlant(UUID plantId) {
        return plantService.
            findOwnerForPlant(plantId)
            .orElseThrow(() -> new IllegalStateException("No owner for plant '%s'".formatted(plantId)));
    }


    long getCareTaskCostCents(CareTaskType taskType) {
        var result = switch (taskType) {
            case PRUNING -> 400L;
            case WATERING -> 50L;
            case REPOTTING -> 500L;
            case FERTILIZING -> 100L;
            case PEST_CONTROL -> 300;
        };

        return result;
    }

}
