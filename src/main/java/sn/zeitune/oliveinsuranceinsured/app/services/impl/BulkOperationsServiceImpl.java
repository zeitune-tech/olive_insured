package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.BulkRiskInsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.BulkRiskInsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.repositories.InsuredRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.BulkOperationsService;
import sn.zeitune.oliveinsuranceinsured.app.services.InsuredService;
import sn.zeitune.oliveinsuranceinsured.app.services.PrimeGarantieService;
import sn.zeitune.oliveinsuranceinsured.app.services.RiskService;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BulkOperationsServiceImpl implements BulkOperationsService {

    private final InsuredService insuredService;
    private final RiskService riskService;
    private final PrimeGarantieService primeGarantieService;
    private final RiskRepository riskRepository;
    private final InsuredRepository insuredRepository;

    @Override
    public BulkRiskInsuredResponse createRiskWithInsuredAndGuarantees(BulkRiskInsuredCreateRequest request) {
        log.debug("Creating risk with insured and guarantees in bulk");

        // 1. Create the insured
        InsuredResponse insuredResponse = insuredService.create(request.insured());
        log.debug("Created insured with UUID: {}", insuredResponse.uuid());

        // 2. Create a risk request with the created insured's UUID
        RiskCreateRequest riskRequest = new RiskCreateRequest(
                request.risk().isFleetMember(),
                request.risk().parentFleetPoliceUuid(),
                request.risk().numAvenant(),
                request.risk().immatriculation(),
                request.risk().ordre(),
                request.risk().marque(),
                request.risk().modele(),
                request.risk().productUuid(),
                request.risk().insuredUuid(),
                request.risk().driverName(),
                request.risk().driverGender(),
                request.risk().driverBirthDate(),
                request.risk().licenseType(),
                request.risk().licenseNumber(),
                request.risk().licenseIssueDate(),
                request.risk().licenseIssuePlace(),
                request.risk().creditDelegation(),
                request.risk().zone(),
                request.risk().vehicleTypeUuid(),
                request.risk().genreUuid(),
                request.risk().usageUuid(),
                request.risk().firstRegistrationDate(),
                request.risk().energie(),
                request.risk().chassisNumber(),
                request.risk().engineNumber(),
                request.risk().bodyType(),
                request.risk().hasTurbo(),
                request.risk().hasTrailer(),
                request.risk().isFlammable(),
                request.risk().power(),
                request.risk().tonnage(),
                request.risk().cylinder(),
                request.risk().seatCount(),
                request.risk().attestationNumberUuid(),
                request.risk().formulaPTUuid(),
                request.risk().nbPersonsTransported(),
                request.risk().maxSpeed(),
                request.risk().newValue(),
                request.risk().marketValue()
        );

        // 3. Create the risk
        RiskResponse riskResponse = riskService.create(riskRequest);
        log.debug("Created risk with UUID: {}", riskResponse.uuid());

        // 4. Create prime garanties if they exist
        List<PrimeGarantieResponse> primeGarantieResponses = null;
        if (request.primesGaranties() != null && !request.primesGaranties().isEmpty()) {
            // Update prime garantie requests with the created risk's UUID
            List<PrimeGarantieCreateRequest> updatedPrimeRequests = request.primesGaranties().stream()
                    .map(primeRequest -> new PrimeGarantieCreateRequest(
                            primeRequest.poSaleUuid(),
                            primeRequest.policeUuid(),
                            riskResponse.uuid(), // UUID of the created risk
                            primeRequest.numAvenant(),
                            primeRequest.quittanceUuid(),
                            primeRequest.produitUuid(),
                            primeRequest.garantieUuid(),
                            primeRequest.primeNette(),
                            primeRequest.taxePrime(),
                            primeRequest.taxeList(),
                            primeRequest.commissionApport(),
                            primeRequest.commissionGestion(),
                            primeRequest.montantReduction(),
                            primeRequest.montantMajoration(),
                            primeRequest.sortGaranti(),
                            primeRequest.compagnieUuid(),
                            primeRequest.createdBy()
                    ))
                    .toList();

            primeGarantieResponses = primeGarantieService.createMultiple(updatedPrimeRequests);
            log.debug("Created {} prime garanties", primeGarantieResponses.size());
        }

        log.info("Successfully created bulk risk-insured-guarantees - Risk UUID: {}, Insured UUID: {}",
                riskResponse.uuid(), insuredResponse.uuid());

        Optional<Risk> risk = riskRepository.findByUuid(riskResponse.uuid());
        Optional<Insured> insured = insuredRepository.findByUuid(insuredResponse.uuid());

        if(risk.isPresent() && insured.isPresent()){
            risk.get().setInsured(insured.get());
            riskRepository.save(risk.get());
        }

        return new BulkRiskInsuredResponse(
                insuredResponse,
                riskResponse,
                primeGarantieResponses != null ? primeGarantieResponses : new ArrayList<>()
        );
    }

    @Override
    public List<BulkRiskInsuredResponse> createMultipleRisksWithInsuredAndGuarantees(List<BulkRiskInsuredCreateRequest> requests) {
        log.debug("Creating {} risks with insured and guarantees in bulk", requests.size());

        return requests.stream()
                .map(this::createRiskWithInsuredAndGuarantees)
                .toList();
    }
}