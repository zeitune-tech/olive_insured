package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ValeurCaracteristiqueRiskUpdateRequest(
        UUID riskUuid,

        UUID caracteristiqueUuid,

        @NotBlank(message = "Valeur cannot be blank")
        String valeur,

        UUID entiteAssocieeUuid
) {
}