package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CaracteristiqueResponse(
        UUID uuid,
        String nom,
        String description,
        Boolean obligatoire,
        Integer ordreAffichage,
        Boolean actif,
        UUID companyUuid,
        List<UUID> warrantiesUuids,
        String typeCaracteristique,
        String typeAttente,

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
        List<OptionCaracteristiqueResponse> options
) {
}