package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record RiskDriverEntryResponse(
        Long id,
        UUID uuid,
        Long riskId,
        LocalDate dateEntree,
        LocalDate dateRetrait,
        Integer avenantEntree,
        Integer avenantRetrait,
        Instant createdAt,
        Instant updatedAt
) {
}