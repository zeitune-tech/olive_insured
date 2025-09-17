package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OptionCaracteristiqueRequest(
        @NotBlank(message = "Valeur must not be blank")
        String valeur,
        
        @NotBlank(message = "Libelle must not be blank")
        String libelle,
        
        Integer ordre
) {}