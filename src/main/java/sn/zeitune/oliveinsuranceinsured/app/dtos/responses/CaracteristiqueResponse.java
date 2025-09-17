package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

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
        String typeAttente
) {
}