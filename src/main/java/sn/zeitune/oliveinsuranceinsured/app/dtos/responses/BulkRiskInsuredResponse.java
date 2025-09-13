package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record BulkRiskInsuredResponse(
        InsuredResponse insured,
        RiskResponse risk,
        List<PrimeGarantieResponse> primesGaranties
) {
}