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
    private final SettingsClient settingsClient;

    @Override
    public InsuredResponse create(InsuredCreateRequest request) {
        validateInsured(request);
        Insured entity = InsuredMapper.toEntity(request);
        // resolve #ref and copy labels
        entity.setVille(request.villeRef());
        entity.setProfession(request.professionRef());
        entity.setActivite(request.activiteRef());
        entity = repository.save(entity);
        return InsuredMapper.toResponse(entity);
    }

    @Override
    public InsuredResponse get(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(InsuredMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));
    }

    @Override
    public Page<InsuredResponse> search(String query, Pageable pageable) {
        if (query == null || query.isBlank()) {
            return repository.findAll(pageable).map(InsuredMapper::toResponse);
        }
        return repository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query, pageable)
                .map(InsuredMapper::toResponse);
    }

    @Override
    public InsuredResponse update(UUID uuid, InsuredUpdateRequest request) {
        Insured insured = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));
        if (request.dateNaissance() != null && request.dateNaissance().isAfter(LocalDate.now()))
            throw new BusinessException("dateNaissance must be in the past");

        // apply simple updates
        InsuredMapper.applyUpdates(insured, request);
        // resolve #ref if provided
        if (request.villeRef() != null) insured.setVille(resolveVille(request.villeRef()));
        if (request.professionRef() != null) insured.setProfession(resolveLabel(request.professionRef(), "profession"));
        if (request.activiteRef() != null) insured.setActivite(resolveLabel(request.activiteRef(), "activite"));
        return InsuredMapper.toResponse(insured);
    }

    @Override
    public void delete(UUID uuid) {
        Insured insured = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Insured not found: " + uuid));
        repository.delete(insured); // soft delete via @SQLDelete
    }

    private void validateInsured(InsuredCreateRequest r) {
        if (r.dateNaissance() != null) {
            if (r.dateNaissance().isAfter(LocalDate.now())) {
                throw new BusinessException("dateNaissance must be in the past");
            }
            if (r.dateNaissance().isAfter(LocalDate.now().minusYears(18))) {
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

