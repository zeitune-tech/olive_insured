package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PrimeGarantieResponse(
        Long id,
        UUID uuid,
        UUID poSaleUuid,
        UUID policeUuid,
        UUID riskUuid,
        String numAvenant,
        UUID quittanceUuid,
        UUID produitUuid,
        UUID garantieUuid,
        BigDecimal primeNette,
        BigDecimal taxePrime,
        List<UUID> taxeList,
        BigDecimal commissionApport,
        BigDecimal commissionGestion,
        BigDecimal montantReduction,
        BigDecimal montantMajoration,
        BigDecimal sortGaranti,
        UUID compagnieUuid,
        Instant createdAt,
        Instant updatedAt,
        String createdBy
) {
}