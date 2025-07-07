package sn.zeitune.oliveinsuranceinsured.app.services;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.VehicleRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.VehicleResponse;

import java.util.List;
import java.util.UUID;

public interface VehicleService {

    VehicleResponse create(VehicleRequest request, UUID managementEntity);

    VehicleResponse getByUuid(UUID uuid);

    List<VehicleResponse> getAll(UUID managementEntity);

    void delete(UUID uuid);
}
