package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

public record OptionCaracteristiqueResponse(
        String valeur,
        String libelle,
        Integer ordre,
        Boolean actif
) {
}