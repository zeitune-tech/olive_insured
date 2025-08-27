package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskCreateRequest(
        @NotBlank String immatriculation,
        Integer ordre,
        String marqueRef,
        String modeleRef,
        UUID genreUuid,
        UUID usageUuid,
        LocalDate dateMiseEnCirculation,
        String energie,
        String numChassis,
        String numMoteur,
        String typeCarrosserie,
        Boolean hasTurbo,
        Boolean hasRemorque,
        Boolean isEnflammable,
        @PositiveOrZero BigDecimal puissance,
        @PositiveOrZero BigDecimal tonnage,
        @PositiveOrZero BigDecimal cylindre,
        @Positive Integer nbPlace,
        UUID numAttestationUuid,
        @PositiveOrZero BigDecimal valeurANeuve,
        @PositiveOrZero BigDecimal valeurVenale
) {}
