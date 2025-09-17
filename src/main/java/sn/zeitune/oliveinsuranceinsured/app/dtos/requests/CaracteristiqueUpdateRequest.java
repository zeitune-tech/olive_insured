package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CaracteristiqueUpdateRequest(
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

        // Propriétés spécifiques CaracteristiqueNumerique
        BigDecimal valeurMin,
        BigDecimal valeurMax,
        Integer nombreDecimales,

        // Propriétés spécifiques CaracteristiqueTexte
        Integer longueurMin,
        Integer longueurMax,
        String regexValidation,

        // Propriétés spécifiques CaracteristiqueListe
        Boolean selectionMultiple,
        List<OptionCaracteristiqueRequest> options
) {
}