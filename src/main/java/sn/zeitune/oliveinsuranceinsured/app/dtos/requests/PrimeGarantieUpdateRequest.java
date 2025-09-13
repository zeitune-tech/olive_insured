package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PrimeGarantieUpdateRequest(
        UUID poSaleUuid,

        UUID policeUuid,

        UUID riskUuid,

        @Size(max = 50, message = "Num avenant must not exceed 50 characters")
        String numAvenant,

        UUID quittanceUuid,

        UUID produitUuid,

        UUID garantieUuid,

        @DecimalMin(value = "0.0", message = "Prime nette must be positive")
        @Digits(integer = 13, fraction = 2, message = "Prime nette must have at most 13 integer digits and 2 decimal places")
        BigDecimal primeNette,

        @DecimalMin(value = "0.0", message = "Taxe prime must be positive")
        @Digits(integer = 13, fraction = 2, message = "Taxe prime must have at most 13 integer digits and 2 decimal places")
        BigDecimal taxePrime,

        List<UUID> taxeList,

        @DecimalMin(value = "0.0", message = "Commission apport must be positive")
        @Digits(integer = 13, fraction = 2, message = "Commission apport must have at most 13 integer digits and 2 decimal places")
        BigDecimal commissionApport,

        @DecimalMin(value = "0.0", message = "Commission gestion must be positive")
        @Digits(integer = 13, fraction = 2, message = "Commission gestion must have at most 13 integer digits and 2 decimal places")
        BigDecimal commissionGestion,

        @DecimalMin(value = "0.0", message = "Montant reduction must be positive")
        @Digits(integer = 13, fraction = 2, message = "Montant reduction must have at most 13 integer digits and 2 decimal places")
        BigDecimal montantReduction,

        @DecimalMin(value = "0.0", message = "Montant majoration must be positive")
        @Digits(integer = 13, fraction = 2, message = "Montant majoration must have at most 13 integer digits and 2 decimal places")
        BigDecimal montantMajoration,

        @DecimalMin(value = "0.0", message = "Sort garanti must be positive")
        @Digits(integer = 13, fraction = 2, message = "Sort garanti must have at most 13 integer digits and 2 decimal places")
        BigDecimal sortGaranti,

        UUID compagnieUuid,

        @Size(max = 100, message = "Created by must not exceed 100 characters")
        String createdBy
) {
}