package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CaracteristiqueCreateRequest(
        @NotBlank(message = "Nom is required")
        @Size(max = 200, message = "Nom must not exceed 200 characters")
        String nom,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        Boolean obligatoire,

        @Min(value = 0, message = "Ordre affichage must be positive")
        Integer ordreAffichage,

        Boolean actif,

        UUID companyUuid,

        List<UUID> warrantiesUuids,

        @NotBlank(message = "Type caracteristique is required")
        String typeCaracteristique
) {
}