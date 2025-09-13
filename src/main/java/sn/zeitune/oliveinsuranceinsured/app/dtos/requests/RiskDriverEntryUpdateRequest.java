package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RiskDriverEntryUpdateRequest(
        Long riskId,

        LocalDate dateEntree,

        @Future(message = "Date retrait must be in the future if provided")
        LocalDate dateRetrait,

        @Min(value = 0, message = "Avenant entree must be positive")
        Integer avenantEntree,

        @Min(value = 0, message = "Avenant retrait must be positive")
        Integer avenantRetrait
) {
    @AssertTrue(message = "Date retrait must be after date entree")
    public boolean isDateRetraitValid() {
        if (dateRetrait == null || dateEntree == null) {
            return true; // Skip validation if either is null
        }
        return dateRetrait.isAfter(dateEntree);
    }
}