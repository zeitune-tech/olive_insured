package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.app.clients.SettingsClient;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.BusinessException;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.app.mappers.InsuredMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.InsuredRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.InsuredService;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuredServiceImpl implements InsuredService {

    private final InsuredRepository repository;
    private final InsuredMapper insuredMapper;
    private final SettingsClient settingsClient;

    @Override
    public InsuredResponse create(InsuredCreateRequest request) {
        validateInsured(request);

        Insured entity = insuredMapper.toEntity(request);

        // Resolve reference labels if provided
        if (request.city() != null) {
            entity.setCity(resolveVille(request.city()));
        }
        if (request.profession() != null) {
            entity.setProfession(resolveLabel(request.profession(), "profession"));
        }
        if (request.activity() != null) {
            entity.setActivity(resolveLabel(request.activity(), "activite"));
        }

        entity = repository.save(entity);
        return insuredMapper.toResponse(entity);
    }

    @Override
    public InsuredResponse get(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(insuredMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));
    }

    @Override
    public Page<InsuredResponse> search(String query, Pageable pageable) {
        if (query == null || query.isBlank()) {
            return repository.findAll(pageable).map(insuredMapper::toResponse);
        }
        return repository
                .findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(query, query, pageable)
                .map(insuredMapper::toResponse);
    }

    @Override
    public InsuredResponse update(UUID uuid, InsuredUpdateRequest request) {
        Insured insured = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));

        // Validate birth date if provided
        if (request.birthDate() != null && request.birthDate().isAfter(LocalDate.now())) {
            throw new BusinessException("Birth date must be in the past");
        }

        // Apply updates using mapper
        insuredMapper.updateEntity(insured, request);

        // Resolve reference labels if provided in update
        if (request.city() != null) {
            insured.setCity(resolveVille(request.city()));
        }
        if (request.profession() != null) {
            insured.setProfession(resolveLabel(request.profession(), "profession"));
        }
        if (request.activity() != null) {
            insured.setActivity(resolveLabel(request.activity(), "activite"));
        }

        insured = repository.save(insured);
        return insuredMapper.toResponse(insured);
    }

    @Override
    public void delete(UUID uuid) {
        Insured insured = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));
        repository.delete(insured); // soft delete via @SQLDelete
    }

    private void validateInsured(InsuredCreateRequest request) {
        if (request.birthDate() != null) {
            if (request.birthDate().isAfter(LocalDate.now())) {
                throw new BusinessException("Birth date must be in the past");
            }
            if (request.birthDate().isAfter(LocalDate.now().minusYears(18))) {
                throw new BusinessException("Assuré doit être majeur (>=18 ans)");
            }
        }
    }

    private String resolveVille(String ref) {
        if (ref == null || ref.isBlank()) return null;
        return settingsClient.searchVilleByName(ref)
                .map(SettingsClient.VilleDto::libelle)
                .orElseThrow(() -> new BusinessException("Ville inconnue: " + ref));
    }

    private String resolveLabel(String ref, String domain) {
        if (ref == null || ref.isBlank()) return null;
        // For demo purposes, copy the ref as label (in real impl, call settings client dedicated endpoints)
        return ref;
    }
}