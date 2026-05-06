package nh.demo.plantify.billing;

import org.springframework.modulith.events.Externalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

// Normalerweise würden hier natürlich ein "echtes" Event nehmen
// und nicht unsere "Entity"

// Demo ist (zu) komplex:
// -> nur erklären
// -> Publish mit "normalem" applicationEventPublisher
@Externalized(target = "invoices::#{#this.ownerId()}")
record Invoice(
    UUID id,
    LocalDateTime createdAt,
    UUID ownerId,
    String ownerName,
    YearMonth billingPeriod,
    BigDecimal amount,
    List<BillingItem> billingItems
) {

    record BillingItem(
        String description,
        BigDecimal amount
    ) {
    }
}



