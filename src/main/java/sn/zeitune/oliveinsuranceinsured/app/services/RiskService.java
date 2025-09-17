package sn.zeitune.oliveinsuranceinsured.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskViewResponse;

import java.util.List;
import java.util.UUID;

public interface RiskService {
    RiskResponse create(RiskCreateRequest request);
    RiskResponse get(UUID uuid);
    Page<RiskResponse> searchByImmatriculation(String immat, Pageable pageable);
    RiskResponse update(UUID uuid, RiskUpdateRequest request);
    void delete(UUID uuid);
    RiskViewResponse getView(UUID uuid);
    List<RiskResponse> findByUuids(List<UUID> uuids);
}

