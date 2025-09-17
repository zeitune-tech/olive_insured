package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.app.clients.SettingsClient;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.ValeurCaracteristiqueRiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskViewResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.ValeurCaracteristiqueRisk;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.BusinessException;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.app.mappers.RiskMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.CaracteristiqueRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.ValeurCaracteristiquePoliceRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.RiskService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final RiskRepository repository;
    private final RiskMapper riskMapper;
    private final SettingsClient settingsClient;
    private final ValeurCaracteristiquePoliceRepository valeurCaracteristiquePoliceRepository;
    private final CaracteristiqueRepository caracteristiqueRepository;

    @Override
    public RiskResponse create(RiskCreateRequest request) {
        validateRefs(request);

        Risk entity = riskMapper.toEntity(request);

        if (request.marque() != null) {
            entity.setMarque(resolveBrandLabel(request.marque()));
        }
        if (request.modele() != null) {
            entity.setModele(resolveModelLabel(entity.getMarque(), request.modele()));
        }

        entity = repository.save(entity);

        if (request.attributes() != null && !request.attributes().isEmpty()) {
            manageAttributes(entity, request.attributes());
        }

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

        if (request.seatCount() != null && request.seatCount() <= 0) {
            throw new BusinessException("Nombre de places doit être > 0");
        }

        riskMapper.updateEntity(entity, request);

        if (request.marque() != null) {
            entity.setMarque(resolveBrandLabel(request.marque()));
        }
        if (request.modele() != null) {
            entity.setModele(resolveModelLabel(entity.getMarque(), request.modele()));
        }

        if (request.attributes() != null) {
            valeurCaracteristiquePoliceRepository.deleteAll(entity.getAttributes());
            entity.getAttributes().clear();

            if (!request.attributes().isEmpty()) {
                manageAttributes(entity, request.attributes());
            }
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

        return riskMapper.toViewResponse(entity);
    }

    @Override
    public List<RiskResponse> findByUuids(List<UUID> uuids) {
        return repository.findByUuidIn(uuids).stream()
                .map(riskMapper::toResponse)
                .toList();
    }

    private void validateRefs(RiskCreateRequest request) {
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
        var models = settingsClient.searchModels(brandLabel != null ? brandLabel : "", ref);
        return models.stream().findFirst()
                .map(SettingsClient.ModelDto::libelle)
                .orElseThrow(() -> new BusinessException("Modèle inconnu: " + ref));
    }

    private void manageAttributes(Risk risk, List<ValeurCaracteristiqueRiskCreateRequest> attributeRequests) {
        validateAttributes(attributeRequests);

        List<ValeurCaracteristiqueRisk> attributes = new ArrayList<>();
        for (ValeurCaracteristiqueRiskCreateRequest attrRequest : attributeRequests) {
            var caracteristique = caracteristiqueRepository.findByUuid(attrRequest.caracteristiqueUuid())
                    .orElseThrow(() -> new BusinessException("Caractéristique inconnue: " + attrRequest.caracteristiqueUuid()));

            if (!caracteristique.validerValeur(attrRequest.valeur())) {
                throw new BusinessException("Valeur invalide pour la caractéristique " + caracteristique.getNom() + ": " + attrRequest.valeur());
            }

            ValeurCaracteristiqueRisk attribute = new ValeurCaracteristiqueRisk();
            attribute.setRisk(risk);
            attribute.setCaracteristique(caracteristique);
            attribute.setValeur(attrRequest.valeur());
            attribute.setEntiteAssocieeUuid(attrRequest.entiteAssocieeUuid());

            attributes.add(attribute);
        }

        valeurCaracteristiquePoliceRepository.saveAll(attributes);
        risk.setAttributes(attributes);
    }

    private void validateAttributes(List<ValeurCaracteristiqueRiskCreateRequest> attributeRequests) {
        if (attributeRequests == null) {
            return;
        }

        for (ValeurCaracteristiqueRiskCreateRequest request : attributeRequests) {
            if (request.caracteristiqueUuid() == null) {
                throw new BusinessException("UUID de caractéristique requis");
            }
            if (request.valeur() == null || request.valeur().isBlank()) {
                throw new BusinessException("Valeur de caractéristique requise");
            }
        }
    }
}