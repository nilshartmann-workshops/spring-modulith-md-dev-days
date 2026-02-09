package nh.demo.plantify.billing.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.moments.support.Moments;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Component
class InvoiceGenerator {

    private static final Logger log = LoggerFactory.getLogger(InvoiceGenerator.class);
    private final UsageRepository usageRepository;

    private final ObjectProvider<Clock> clockProvider;

    InvoiceGenerator(UsageRepository usageRepository, ApplicationEventPublisher applicationEventPublisher, ObjectProvider<Clock> clockProvider) {
        this.usageRepository = usageRepository;
        this.clockProvider = clockProvider;
    }

    private void generateInvoicesForMonth(YearMonth month, Consumer<Invoice> onInvoiceGenerated) {
        Instant start = month.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = month.atEndOfMonth().atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        var invoiceCreatedAt = LocalDateTime.now(clockProvider.getIfAvailable(Clock::systemDefaultZone));

        List<UUID> ownerIds = usageRepository.findOwnerIdsBetween(
            start, end
        );

        ownerIds.forEach(ownerId -> {
                var usageRecords = usageRepository.getUsagesForOwnerRecordedBetween(
                    ownerId, start, end
                );


                var amount = BigDecimal.valueOf(
                        usageRecords
                            .stream()
                            .mapToLong(UsageRecord::getCostCents)
                            .sum()
                    )
                    .movePointLeft(2);

                var invoice = new Invoice(
                    invoiceCreatedAt,
                    ownerId,
                    month,
                    amount,
                    usageRecords.stream().map(BillingItem::of).toList()
                );

                onInvoiceGenerated.accept(invoice);
            }
        );
    }

    private BigDecimal calculateCostsEuroForOwner(UUID ownerId, Instant start, Instant end) {
        var costsCents = usageRepository.getTotalCostsForOwnerRecordedBetween(
            ownerId,
            start,
            end
        );

        return BigDecimal.valueOf(costsCents).movePointLeft(2);
    }

}
