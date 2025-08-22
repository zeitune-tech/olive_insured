package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskUpdateRequest(
        String immatriculation,
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
        BigDecimal puissance,
        BigDecimal tonnage,
        BigDecimal cylindre,
        Integer nbPlace,
        UUID numAttestationUuid,
        BigDecimal valeurANeuve,
        BigDecimal valeurVenale
) {}