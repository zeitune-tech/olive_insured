package sn.zeitune.oliveinsuranceinsured.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;

import java.util.UUID;

public interface InsuredService {
    InsuredResponse create(InsuredCreateRequest request);
    InsuredResponse get(UUID uuid);
    Page<InsuredResponse> search(String query, Pageable pageable);
    InsuredResponse update(UUID uuid, InsuredUpdateRequest request);
    void delete(UUID uuid);
}

