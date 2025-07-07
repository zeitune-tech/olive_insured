package sn.zeitune.oliveinsuranceinsured.app.services.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;
import sn.zeitune.oliveinsuranceinsured.app.mappers.InsuredMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.InsuredRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.InsuredService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuredServiceImpl implements InsuredService {

    private final InsuredRepository repository;

    @Override
    public InsuredResponse create(InsuredRequest request, UUID managementEntity) {
        Insured entity = InsuredMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return InsuredMapper.map(entity);
    }

    @Override
    public InsuredResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(InsuredMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Insured not found"));
    }

    @Override
    public List<InsuredResponse> getAll(UUID managementEntity) {
        return repository.findAllByManagementEntity(managementEntity).stream()
                .map(InsuredMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Insured insured = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Insured not found"));
        repository.delete(insured);
    }
}
