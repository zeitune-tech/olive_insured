package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskViewResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RiskMapper {

    @Autowired
    private InsuredMapper insuredMapper;

    @Autowired
    private RiskDriverEntryMapper riskDriverEntryMapper;

    @Autowired
    private PrimeGarantieMapper primeGarantieMapper;

    /**
     * Converts RiskCreateRequest to Risk entity using builder pattern
     */
    public Risk toEntity(RiskCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Risk.builder()
                .isFleetMember(request.isFleetMember())
                .parentFleetPoliceUuid(request.parentFleetPoliceUuid())
                .numAvenant(request.numAvenant())
                .immatriculation(request.immatriculation())
                .ordre(request.ordre())
                .marque(request.marque())
                .modele(request.modele())
                .productUuid(request.productUuid())
                .driverName(request.driverName())
                .driverGender(request.driverGender())
                .driverBirthDate(request.driverBirthDate())
                .licenseType(request.licenseType())
                .licenseNumber(request.licenseNumber())
                .licenseIssueDate(request.licenseIssueDate())
                .licenseIssuePlace(request.licenseIssuePlace())
                .creditDelegation(request.creditDelegation())
                .zone(request.zone())
                .vehicleTypeUuid(request.vehicleTypeUuid())
                .usageUuid(request.usageUuid())
                .firstRegistrationDate(request.firstRegistrationDate())
                .energie(request.energie())
                .chassisNumber(request.chassisNumber())
                .engineNumber(request.engineNumber())
                .bodyType(request.bodyType())
                .hasTurbo(request.hasTurbo())
                .hasTrailer(request.hasTrailer())
                .isFlammable(request.isFlammable())
                .power(request.power())
                .tonnage(request.tonnage())
                .cylinder(request.cylinder())
                .seatCount(request.seatCount())
                .attestationNumberUuid(request.attestationNumberUuid())
                .formulaPTUuid(request.formulaPTUuid())
                .nbPersonsTransported(request.nbPersonsTransported())
                .maxSpeed(request.maxSpeed())
                .newValue(request.newValue())
                .marketValue(request.marketValue())
                .build();
    }

    /**
     * Converts Risk entity to RiskResponse using builder pattern
     */
    public RiskResponse toResponse(Risk entity) {
        if (entity == null) {
            return null;
        }

        return new RiskResponse(
                entity.getId(),
                entity.getUuid(),
                entity.getIsFleetMember(),
                entity.getParentFleetPoliceUuid(),
                entity.getNumAvenant(),
                entity.getImmatriculation(),
                entity.getOrdre(),
                entity.getMarque(),
                entity.getModele(),
                entity.getProductUuid(),
                entity.getDriverName(),
                entity.getDriverGender(),
                entity.getDriverBirthDate(),
                entity.getLicenseType(),
                entity.getLicenseNumber(),
                entity.getLicenseIssueDate(),
                entity.getLicenseIssuePlace(),
                entity.getCreditDelegation(),
                entity.getZone(),
                entity.getVehicleTypeUuid(),
                entity.getUsageUuid(),
                entity.getFirstRegistrationDate(),
                entity.getEnergie(),
                entity.getChassisNumber(),
                entity.getEngineNumber(),
                entity.getBodyType(),
                entity.getHasTurbo(),
                entity.getHasTrailer(),
                entity.getIsFlammable(),
                entity.getPower(),
                entity.getTonnage(),
                entity.getCylinder(),
                entity.getSeatCount(),
                entity.getAttestationNumberUuid(),
                entity.getFormulaPTUuid(),
                entity.getNbPersonsTransported(),
                entity.getMaxSpeed(),
                entity.getNewValue(),
                entity.getMarketValue(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts Risk entity to RiskViewResponse with nested relationships
     */
    public RiskViewResponse toViewResponse(Risk entity) {
        if (entity == null) {
            return null;
        }

        return new RiskViewResponse(
                entity.getId(),
                entity.getUuid(),
                entity.getIsFleetMember(),
                entity.getParentFleetPoliceUuid(),
                entity.getNumAvenant(),
                entity.getImmatriculation(),
                entity.getOrdre(),
                entity.getMarque(),
                entity.getModele(),
                entity.getProductUuid(),
                // Nested insured information
                entity.getInsured() != null ? insuredMapper.toResponse(entity.getInsured()) : null,
                // Driver entries list
                entity.getDriverEntries() != null ?
                        entity.getDriverEntries().stream()
                                .map(riskDriverEntryMapper::toResponse)
                                .collect(Collectors.toList()) : Collections.emptyList(),
                entity.getDriverName(),
                entity.getDriverGender(),
                entity.getDriverBirthDate(),
                entity.getLicenseType(),
                entity.getLicenseNumber(),
                entity.getLicenseIssueDate(),
                entity.getLicenseIssuePlace(),
                entity.getCreditDelegation(),
                entity.getZone(),
                entity.getVehicleTypeUuid(),
                entity.getUsageUuid(),
                entity.getFirstRegistrationDate(),
                entity.getEnergie(),
                entity.getChassisNumber(),
                entity.getEngineNumber(),
                entity.getBodyType(),
                entity.getHasTurbo(),
                entity.getHasTrailer(),
                entity.getIsFlammable(),
                entity.getPower(),
                entity.getTonnage(),
                entity.getCylinder(),
                entity.getSeatCount(),
                entity.getAttestationNumberUuid(),
                entity.getFormulaPTUuid(),
                entity.getNbPersonsTransported(),
                entity.getMaxSpeed(),
                entity.getNewValue(),
                entity.getMarketValue(),
                // Primes garanties list
                entity.getPrimesGaranties() != null ?
                        entity.getPrimesGaranties().stream()
                                .map(primeGarantieMapper::toResponse)
                                .collect(Collectors.toList()) : Collections.emptyList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Updates existing Risk entity with data from RiskUpdateRequest
     * Only updates non-null fields from the request
     */
    public void updateEntity(Risk entity, RiskUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.isFleetMember() != null) {
            entity.setIsFleetMember(request.isFleetMember());
        }
        if (request.parentFleetPoliceUuid() != null) {
            entity.setParentFleetPoliceUuid(request.parentFleetPoliceUuid());
        }
        if (request.numAvenant() != null) {
            entity.setNumAvenant(request.numAvenant());
        }
        if (request.immatriculation() != null) {
            entity.setImmatriculation(request.immatriculation());
        }
        if (request.ordre() != null) {
            entity.setOrdre(request.ordre());
        }
        if (request.marque() != null) {
            entity.setMarque(request.marque());
        }
        if (request.modele() != null) {
            entity.setModele(request.modele());
        }
        if (request.productUuid() != null) {
            entity.setProductUuid(request.productUuid());
        }
        if (request.driverName() != null) {
            entity.setDriverName(request.driverName());
        }
        if (request.driverGender() != null) {
            entity.setDriverGender(request.driverGender());
        }
        if (request.driverBirthDate() != null) {
            entity.setDriverBirthDate(request.driverBirthDate());
        }
        if (request.licenseType() != null) {
            entity.setLicenseType(request.licenseType());
        }
        if (request.licenseNumber() != null) {
            entity.setLicenseNumber(request.licenseNumber());
        }
        if (request.licenseIssueDate() != null) {
            entity.setLicenseIssueDate(request.licenseIssueDate());
        }
        if (request.licenseIssuePlace() != null) {
            entity.setLicenseIssuePlace(request.licenseIssuePlace());
        }
        if (request.creditDelegation() != null) {
            entity.setCreditDelegation(request.creditDelegation());
        }
        if (request.zone() != null) {
            entity.setZone(request.zone());
        }
        if (request.vehicleTypeUuid() != null) {
            entity.setVehicleTypeUuid(request.vehicleTypeUuid());
        }
        if (request.usageUuid() != null) {
            entity.setUsageUuid(request.usageUuid());
        }
        if (request.firstRegistrationDate() != null) {
            entity.setFirstRegistrationDate(request.firstRegistrationDate());
        }
        if (request.energie() != null) {
            entity.setEnergie(request.energie());
        }
        if (request.chassisNumber() != null) {
            entity.setChassisNumber(request.chassisNumber());
        }
        if (request.engineNumber() != null) {
            entity.setEngineNumber(request.engineNumber());
        }
        if (request.bodyType() != null) {
            entity.setBodyType(request.bodyType());
        }
        if (request.hasTurbo() != null) {
            entity.setHasTurbo(request.hasTurbo());
        }
        if (request.hasTrailer() != null) {
            entity.setHasTrailer(request.hasTrailer());
        }
        if (request.isFlammable() != null) {
            entity.setIsFlammable(request.isFlammable());
        }
        if (request.power() != null) {
            entity.setPower(request.power());
        }
        if (request.tonnage() != null) {
            entity.setTonnage(request.tonnage());
        }
        if (request.cylinder() != null) {
            entity.setCylinder(request.cylinder());
        }
        if (request.seatCount() != null) {
            entity.setSeatCount(request.seatCount());
        }
        if (request.attestationNumberUuid() != null) {
            entity.setAttestationNumberUuid(request.attestationNumberUuid());
        }
        if (request.formulaPTUuid() != null) {
            entity.setFormulaPTUuid(request.formulaPTUuid());
        }
        if (request.nbPersonsTransported() != null) {
            entity.setNbPersonsTransported(request.nbPersonsTransported());
        }
        if (request.maxSpeed() != null) {
            entity.setMaxSpeed(request.maxSpeed());
        }
        if (request.newValue() != null) {
            entity.setNewValue(request.newValue());
        }
        if (request.marketValue() != null) {
            entity.setMarketValue(request.marketValue());
        }
    }
}