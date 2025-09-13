package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.entities.PrimeGarantie;

import java.util.ArrayList;

@Component
public class PrimeGarantieMapper {

    /**
     * Converts PrimeGarantieCreateRequest to PrimeGarantie entity using builder pattern
     */
    public PrimeGarantie toEntity(PrimeGarantieCreateRequest request) {
        if (request == null) {
            return null;
        }

        return PrimeGarantie.builder()
                .poSaleUuid(request.poSaleUuid())
                .policeUuid(request.policeUuid())
                .riskUuid(request.riskUuid())
                .numAvenant(request.numAvenant())
                .quittanceUuid(request.quittanceUuid())
                .produitUuid(request.produitUuid())
                .garantieUuid(request.garantieUuid())
                .primeNette(request.primeNette())
                .taxePrime(request.taxePrime())
                .taxeList(request.taxeList() != null ? new ArrayList<>(request.taxeList()) : new ArrayList<>())
                .commissionApport(request.commissionApport())
                .commissionGestion(request.commissionGestion())
                .montantReduction(request.montantReduction())
                .montantMajoration(request.montantMajoration())
                .sortGaranti(request.sortGaranti())
                .compagnieUuid(request.compagnieUuid())
                .createdBy(request.createdBy())
                .build();
    }

    /**
     * Converts PrimeGarantie entity to PrimeGarantieResponse
     */
    public PrimeGarantieResponse toResponse(PrimeGarantie entity) {
        if (entity == null) {
            return null;
        }

        return new PrimeGarantieResponse(
                entity.getId(),
                entity.getUuid(),
                entity.getPoSaleUuid(),
                entity.getPoliceUuid(),
                entity.getRiskUuid(),
                entity.getNumAvenant(),
                entity.getQuittanceUuid(),
                entity.getProduitUuid(),
                entity.getGarantieUuid(),
                entity.getPrimeNette(),
                entity.getTaxePrime(),
                entity.getTaxeList(),
                entity.getCommissionApport(),
                entity.getCommissionGestion(),
                entity.getMontantReduction(),
                entity.getMontantMajoration(),
                entity.getSortGaranti(),
                entity.getCompagnieUuid(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy()
        );
    }

    /**
     * Updates existing PrimeGarantie entity with data from PrimeGarantieUpdateRequest
     * Only updates non-null fields from the request
     */
    public void updateEntity(PrimeGarantie entity, PrimeGarantieUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.poSaleUuid() != null) {
            entity.setPoSaleUuid(request.poSaleUuid());
        }
        if (request.policeUuid() != null) {
            entity.setPoliceUuid(request.policeUuid());
        }
        if (request.riskUuid() != null) {
            entity.setRiskUuid(request.riskUuid());
        }
        if (request.numAvenant() != null) {
            entity.setNumAvenant(request.numAvenant());
        }
        if (request.quittanceUuid() != null) {
            entity.setQuittanceUuid(request.quittanceUuid());
        }
        if (request.produitUuid() != null) {
            entity.setProduitUuid(request.produitUuid());
        }
        if (request.garantieUuid() != null) {
            entity.setGarantieUuid(request.garantieUuid());
        }
        if (request.primeNette() != null) {
            entity.setPrimeNette(request.primeNette());
        }
        if (request.taxePrime() != null) {
            entity.setTaxePrime(request.taxePrime());
        }
        if (request.taxeList() != null) {
            entity.setTaxeList(new ArrayList<>(request.taxeList()));
        }
        if (request.commissionApport() != null) {
            entity.setCommissionApport(request.commissionApport());
        }
        if (request.commissionGestion() != null) {
            entity.setCommissionGestion(request.commissionGestion());
        }
        if (request.montantReduction() != null) {
            entity.setMontantReduction(request.montantReduction());
        }
        if (request.montantMajoration() != null) {
            entity.setMontantMajoration(request.montantMajoration());
        }
        if (request.sortGaranti() != null) {
            entity.setSortGaranti(request.sortGaranti());
        }
        if (request.compagnieUuid() != null) {
            entity.setCompagnieUuid(request.compagnieUuid());
        }
        if (request.createdBy() != null) {
            entity.setCreatedBy(request.createdBy());
        }
    }
}