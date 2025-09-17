package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import java.util.UUID;

public record ValeurCaracteristiqueRiskResponse(
        UUID riskUuid,
        CaracteristiqueResponse caracteristique,
        String valeur,
        UUID entiteAssocieeUuid
) {
}