package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskResponse(
        UUID uuid,
        String immatriculation,
        Integer ordre,
        String marque,
        String modele,
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
        BigDecimal valeurVenale,
        UUID insuredUuid,
        String nomConducteur,
        String sexeConducteur,
        LocalDate dateNaissanceConducteur,
        String typePermis,
        String numPermis,
        LocalDate dateDelivrancePermis,
        String lieuDelivrancePermis,
        Boolean delegationCredit,
        String zone,
        Instant createdAt,
        Instant updatedAt
) {}