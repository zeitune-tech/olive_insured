package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ValeurCaracteristiqueRiskCreateRequest(
        @NotNull(message = "Police ID is required")
        UUID riskUuid,

        @NotNull(message = "Caracteristique ID is required")
        UUID caracteristiqueUuid,

        @NotBlank(message = "Valeur is required")
        String valeur,

        UUID entiteAssocieeUuid
) {
}