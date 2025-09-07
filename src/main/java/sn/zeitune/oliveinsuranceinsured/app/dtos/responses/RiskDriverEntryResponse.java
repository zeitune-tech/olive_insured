package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskDriverEntryResponse(
        UUID uuid,
        UUID riskUuid,
        LocalDate dateEntree,
        LocalDate dateRetrait,
        Integer avenantEntree,
        Integer avenantRetrait,
        Instant createdAt,
        Instant updatedAt
) {}