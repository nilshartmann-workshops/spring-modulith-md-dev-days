package nh.demo.plantify.billing;

import nh.demo.plantify.plant.PlantRegisteredEvent;
import nh.demo.plantify.shared.CareTaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.UUID;

@Service
public class UsageTracker {

    private static final Logger log = LoggerFactory.getLogger(UsageTracker.class);
    private final UsageRepository usageRepository;

    UsageTracker(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

//    @TransactionalEventListener
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Async
    @ApplicationModuleListener
    void onPlantCreated(PlantRegisteredEvent event) {
        if (true) {
            // 🤔 Was passiert wenn hier was schiefgeht?
            // Vor dem zeigen nochmal DB leer machen!
            //  - Tabellen "plant" und "care_tasks" befüllt ✅
            //  - usage_record nicht, wir bekommen also kein Geld 😢
            //
            // 🕵️‍♂️ publication_registry-Tabelle
            //   - Zwei Einträge (pro Listener einer)
            //   - State ansehen (einmal COMPLETED, einmal FAILED)
            throw new RuntimeException("Nö");
        }

        registerSetupFee(event.plantId(), event.ownerId());
    }

    private void registerSetupFee(UUID plantId, UUID ownerId) {
        UsageRecord usageRecord = new UsageRecord(
            ownerId,
            UsageRecord.UsageType.SETUP_FEE,
            Instant.now(),
            1000L
        );

        usageRepository.save(usageRecord);

        log.info("""
            
            
            🤑
            🤑 Setup Fee registered for plant '{}'
            🤑
            
            """, plantId);
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
