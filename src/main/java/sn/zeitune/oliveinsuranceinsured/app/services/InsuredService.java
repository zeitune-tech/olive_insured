package sn.zeitune.oliveinsuranceinsured.app.services;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;

import java.util.List;
import java.util.UUID;

public interface InsuredService {

    InsuredResponse create(InsuredRequest request, UUID managementEntity);

    InsuredResponse getByUuid(UUID uuid);

    List<InsuredResponse> getAll(UUID managementEntity);

    void delete(UUID uuid);
}
