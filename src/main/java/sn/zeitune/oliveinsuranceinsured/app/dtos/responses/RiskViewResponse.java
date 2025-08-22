package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RiskViewResponse(
        UUID uuid,
        String immatriculation,
        String marque,
        String modele,
        RefDto genre,
        RefDto usage,
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
        AttestationDto attestation,
        BigDecimal valeurANeuve,
        BigDecimal valeurVenale,
        Instant createdAt,
        Instant updatedAt
) {
    public record RefDto(UUID uuid, String code, String libelle) {}
    public record AttestationDto(UUID uuid, String numero, String statut) {}
}