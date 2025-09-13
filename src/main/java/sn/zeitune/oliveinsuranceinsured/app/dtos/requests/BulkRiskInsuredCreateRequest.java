package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BulkRiskInsuredCreateRequest(
        @Valid @NotNull InsuredCreateRequest insured,
        @Valid @NotNull RiskCreateRequest risk,
        @Valid List<PrimeGarantieCreateRequest> primesGaranties
) {
}