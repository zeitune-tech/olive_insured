package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.PrimeGarantie;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.app.mappers.PrimeGarantieMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.PrimeGarantieRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.PrimeGarantieService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrimeGarantieServiceImpl implements PrimeGarantieService {

    private final PrimeGarantieRepository repository;
    private final PrimeGarantieMapper primeGarantieMapper;

    @Override
    public PrimeGarantieResponse create(PrimeGarantieCreateRequest request) {
        log.debug("Creating new PrimeGarantie for risk: {}", request.riskUuid());

        PrimeGarantie entity = primeGarantieMapper.toEntity(request);
        PrimeGarantie saved = repository.save(entity);

        log.info("Created PrimeGarantie with UUID: {}", saved.getUuid());
        return primeGarantieMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PrimeGarantieResponse findByUuid(UUID uuid) {
        log.debug("Finding PrimeGarantie by UUID: {}", uuid);

        PrimeGarantie entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("PrimeGarantie not found with UUID: " + uuid));

        return primeGarantieMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrimeGarantieResponse> findByRiskUuid(UUID riskUuid) {
        log.debug("Finding PrimeGaranties for risk: {}", riskUuid);

        List<PrimeGarantie> entities = repository.findByRiskUuidAndDeletedFalse(riskUuid);
        return entities.stream()
                .map(primeGarantieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrimeGarantieResponse> findByPoliceUuid(UUID policeUuid) {
        log.debug("Finding PrimeGaranties for police: {}", policeUuid);

        List<PrimeGarantie> entities = repository.findByPoliceUuidAndDeletedFalse(policeUuid);
        return entities.stream()
                .map(primeGarantieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrimeGarantieResponse> findAll(Pageable pageable) {
        log.debug("Finding all PrimeGaranties with pagination");

        return repository.findByDeletedFalse(pageable)
                .map(primeGarantieMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrimeGarantieResponse> findWithFilters(UUID policeUuid, UUID riskUuid, UUID garantieUuid, Pageable pageable) {
        log.debug("Finding PrimeGaranties with filters - police: {}, risk: {}, garantie: {}", policeUuid, riskUuid, garantieUuid);

        return repository.findWithFilters(policeUuid, riskUuid, garantieUuid, pageable)
                .map(primeGarantieMapper::toResponse);
    }

    @Override
    public PrimeGarantieResponse update(UUID uuid, PrimeGarantieUpdateRequest request) {
        log.debug("Updating PrimeGarantie: {}", uuid);

        PrimeGarantie entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("PrimeGarantie not found with UUID: " + uuid));

        primeGarantieMapper.updateEntity(entity, request);
        PrimeGarantie updated = repository.save(entity);

        log.info("Updated PrimeGarantie: {}", uuid);
        return primeGarantieMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID uuid) {
        log.debug("Deleting PrimeGarantie: {}", uuid);

        PrimeGarantie entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("PrimeGarantie not found with UUID: " + uuid));

        entity.setDeleted(true);
        repository.save(entity);

        log.info("Deleted PrimeGarantie: {}", uuid);
    }

    @Override
    public List<PrimeGarantieResponse> createMultiple(List<PrimeGarantieCreateRequest> requests) {
        log.debug("Creating {} PrimeGaranties", requests.size());

        List<PrimeGarantie> entities = requests.stream()
                .map(primeGarantieMapper::toEntity)
                .toList();

        List<PrimeGarantie> saved = repository.saveAll(entities);

        log.info("Created {} PrimeGaranties", saved.size());
        return saved.stream()
                .map(primeGarantieMapper::toResponse)
                .toList();
    }
}