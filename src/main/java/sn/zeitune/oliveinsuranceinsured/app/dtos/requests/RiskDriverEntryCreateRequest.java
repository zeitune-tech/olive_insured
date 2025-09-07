package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskDriverEntryCreateRequest(
        @NotNull UUID riskUuid,
        @NotNull LocalDate dateEntree,
        LocalDate dateRetrait,
        Integer avenantEntree,
        Integer avenantRetrait
) {}