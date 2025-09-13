package sn.zeitune.oliveinsuranceinsured.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;

import java.util.List;
import java.util.UUID;

public interface PrimeGarantieService {

    PrimeGarantieResponse create(PrimeGarantieCreateRequest request);

    PrimeGarantieResponse findByUuid(UUID uuid);

    List<PrimeGarantieResponse> findByRiskUuid(UUID riskUuid);

    List<PrimeGarantieResponse> findByPoliceUuid(UUID policeUuid);

    Page<PrimeGarantieResponse> findAll(Pageable pageable);

    Page<PrimeGarantieResponse> findWithFilters(UUID policeUuid, UUID riskUuid, UUID garantieUuid, Pageable pageable);

    PrimeGarantieResponse update(UUID uuid, PrimeGarantieUpdateRequest request);

    void delete(UUID uuid);

    List<PrimeGarantieResponse> createMultiple(List<PrimeGarantieCreateRequest> requests);
}