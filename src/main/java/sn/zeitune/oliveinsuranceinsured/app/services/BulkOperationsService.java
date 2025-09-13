package sn.zeitune.oliveinsuranceinsured.app.services;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.BulkRiskInsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.BulkRiskInsuredResponse;

import java.util.List;

public interface BulkOperationsService {

    BulkRiskInsuredResponse createRiskWithInsuredAndGuarantees(BulkRiskInsuredCreateRequest request);

    List<BulkRiskInsuredResponse> createMultipleRisksWithInsuredAndGuarantees(List<BulkRiskInsuredCreateRequest> requests);
}