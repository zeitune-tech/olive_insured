package sn.zeitune.oliveinsuranceinsured.app.controllers.interservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.BulkRiskInsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.BulkRiskInsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.BulkOperationsService;

import java.util.List;

@RestController
@RequestMapping("/interservices/bulk")
@RequiredArgsConstructor
@Slf4j
public class BulkInterserviceController {

    private final BulkOperationsService bulkOperationsService;

    @PostMapping("/risk-insured-guarantees")
    public ResponseEntity<BulkRiskInsuredResponse> createRiskWithInsuredAndGuarantees(
            @Valid @RequestBody BulkRiskInsuredCreateRequest request
    ) {
        log.info("Inter-service: Creating risk with insured and guarantees in bulk operation");
        BulkRiskInsuredResponse response = bulkOperationsService.createRiskWithInsuredAndGuarantees(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/multiple-risk-insured-guarantees")
    public ResponseEntity<List<BulkRiskInsuredResponse>> createMultipleRisksWithInsuredAndGuarantees(
            @Valid @RequestBody List<BulkRiskInsuredCreateRequest> requests
    ) {
        log.info("Inter-service: Creating {} risks with insured and guarantees in bulk operation", requests.size());
        List<BulkRiskInsuredResponse> responses = bulkOperationsService.createMultipleRisksWithInsuredAndGuarantees(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}