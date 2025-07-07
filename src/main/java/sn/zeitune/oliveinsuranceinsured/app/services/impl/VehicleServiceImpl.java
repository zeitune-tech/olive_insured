package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.VehicleRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.VehicleResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;
import sn.zeitune.oliveinsuranceinsured.app.entities.Vehicle;
import sn.zeitune.oliveinsuranceinsured.app.mappers.InsuredMapper;
import sn.zeitune.oliveinsuranceinsured.app.mappers.VehicleMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.InsuredRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.VehicleRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.VehicleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;
    private final InsuredRepository insuredRepository;

    @Override
    public VehicleResponse create(VehicleRequest request, UUID managementEntity) {
        Insured  insured = insuredRepository.findByUuid(request.insuredId())
                .orElseThrow(() -> new IllegalArgumentException("Insured not found"));

        Vehicle vehicle = VehicleMapper.map(request);
        vehicle.setManagementEntity(managementEntity);
        vehicle.setInsured(insured);
        vehicle = repository.save(vehicle);


        return VehicleMapper.map(vehicle, InsuredMapper.map(insured));
    }

    @Override
    public VehicleResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(vehicle -> VehicleMapper.map(vehicle, InsuredMapper.map(vehicle.getInsured()))
                )
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }

    @Override
    public List<VehicleResponse> getAll(UUID managementEntity) {
        List<Vehicle> vehicles = repository.findAllByManagementEntity(managementEntity);

        return vehicles.stream()
                .map(vehicle -> VehicleMapper.map(vehicle, InsuredMapper.map(vehicle.getInsured())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Vehicle vehicle = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        repository.delete(vehicle);
    }
}
