package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.app.clients.AttestationClient;
import sn.zeitune.oliveinsuranceinsured.app.clients.SettingsClient;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskViewResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.BusinessException;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.app.mappers.RiskMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.InsuredRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.RiskService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final RiskRepository repository;
    private final RiskMapper riskMapper;
    private final SettingsClient settingsClient;
    private final AttestationClient attestationClient;
    private final InsuredRepository insuredRepository;

    @Override
    public RiskResponse create(RiskCreateRequest request) {
        validateRefs(request);

        Risk entity = riskMapper.toEntity(request);

        // Establish a relationship with Insured
        if (request.insuredUuid() != null) {
            var insured = insuredRepository.findByUuid(request.insuredUuid())
                    .orElseThrow(() -> new NotFoundException("Insured not found: " + request.insuredUuid()));
            entity.setInsured(insured);
        }

        // Resolve and copy labels
        if (request.marque() != null) {
            entity.setMarque(resolveBrandLabel(request.marque()));
        }
        if (request.modele() != null) {
            entity.setModele(resolveModelLabel(entity.getMarque(), request.modele()));
        }

        entity = repository.save(entity);
        return riskMapper.toResponse(entity);
    }

    @Override
    public RiskResponse get(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(riskMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
    }

    @Override
    public Page<RiskResponse> searchByImmatriculation(String immat, Pageable pageable) {
        if (immat == null || immat.isBlank()) {
            return repository.findAll(pageable).map(riskMapper::toResponse);
        }
        return repository.findAllByImmatriculationContainingIgnoreCase(immat, pageable)
                .map(riskMapper::toResponse);
    }

    @Override
    public RiskResponse update(UUID uuid, RiskUpdateRequest request) {
        Risk entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));

        // Simple validation for basic fields
        if (request.seatCount() != null && request.seatCount() <= 0) {
            throw new BusinessException("Nombre de places doit être > 0");
        }

        // Apply updates using mapper
        riskMapper.updateEntity(entity, request);

        // Update relationship with Insured if provided
        if (request.insuredUuid() != null) {
            var insured = insuredRepository.findByUuid(request.insuredUuid())
                    .orElseThrow(() -> new NotFoundException("Insured not found: " + request.insuredUuid()));
            entity.setInsured(insured);
        }

        // Resolve labels if brand/model references are provided
        if (request.marque() != null) {
            entity.setMarque(resolveBrandLabel(request.marque()));
        }
        if (request.modele() != null) {
            entity.setModele(resolveModelLabel(entity.getMarque(), request.modele()));
        }

        entity = repository.save(entity);
        return riskMapper.toResponse(entity);
    }

    @Override
    public void delete(UUID uuid) {
        Risk entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
        repository.delete(entity);
    }

    @Override
    public RiskViewResponse getView(UUID uuid) {
        Risk entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));

        // Use the mapper's toViewResponse method, which handles nested relationships
        return riskMapper.toViewResponse(entity);
    }

    @Override
    public List<RiskResponse> findByUuids(List<UUID> uuids) {
        return repository.findByUuidIn(uuids).stream()
                .map(riskMapper::toResponse)
                .toList();
    }

    @Override
    public List<RiskResponse> findByInsuredUuid(UUID insuredUuid) {
        return repository.findByInsuredUuid(insuredUuid).stream()
                .map(riskMapper::toResponse)
                .toList();
    }

    private void validateRefs(RiskCreateRequest request) {
        // Uncomment when ready to validate external references
        /*
        if (request.vehicleTypeUuid() != null && settingsClient.getVehicleType(request.vehicleTypeUuid()).isEmpty())
            throw new BusinessException("Type véhicule introuvable: " + request.vehicleTypeUuid());
        if (request.usageUuid() != null && settingsClient.getUsage(request.usageUuid()).isEmpty())
            throw new BusinessException("Usage introuvable: " + request.usageUuid());
        if (request.attestationNumberUuid() != null && attestationClient.get(request.attestationNumberUuid()).isEmpty())
            throw new BusinessException("Attestation introuvable: " + request.attestationNumberUuid());
        */

        if (request.seatCount() != null && request.seatCount() <= 0) {
            throw new BusinessException("Nombre de places doit être > 0");
        }
    }

    private String resolveBrandLabel(String ref) {
        if (ref == null || ref.isBlank()) return null;
        var brands = settingsClient.searchBrands(ref);
        return brands.stream().findFirst()
                .map(SettingsClient.BrandDto::libelle)
                .orElseThrow(() -> new BusinessException("Marque inconnue: " + ref));
    }

    private String resolveModelLabel(String brandLabel, String ref) {
        if (ref == null || ref.isBlank()) return null;
        // If brand is known, try scoped model search, else fallback
        var models = settingsClient.searchModels(brandLabel != null ? brandLabel : "", ref);
        return models.stream().findFirst()
                .map(SettingsClient.ModelDto::libelle)
                .orElseThrow(() -> new BusinessException("Modèle inconnu: " + ref));
    }
}