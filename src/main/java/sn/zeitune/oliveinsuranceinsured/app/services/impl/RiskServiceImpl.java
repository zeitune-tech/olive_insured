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
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.RiskService;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final RiskRepository repository;
    private final SettingsClient settingsClient;
    private final AttestationClient attestationClient;

    @Override
    public RiskResponse create(RiskCreateRequest request) {
        validateRefs(request);
        Risk entity = RiskMapper.toEntity(request);
        // #ref: copy labels
        entity.setMarque(resolveBrandLabel(request.marqueRef()));
        entity.setModele(resolveModelLabel(entity.getMarque(), request.modeleRef()));
        entity = repository.save(entity);
        return RiskMapper.toResponse(entity);
    }

    @Override
    public RiskResponse get(UUID uuid) {
        return repository.findByUuid(uuid).map(RiskMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
    }

    @Override
    public Page<RiskResponse> searchByImmatriculation(String immat, Pageable pageable) {
        if (immat == null || immat.isBlank()) {
            return repository.findAll(pageable).map(RiskMapper::toResponse);
        }
        return repository.findAllByImmatriculationContainingIgnoreCase(immat, pageable).map(RiskMapper::toResponse);
    }

    @Override
    public RiskResponse update(UUID uuid, RiskUpdateRequest request) {
        Risk entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
        if (request.genreUuid() != null || request.usageUuid() != null || request.numAttestationUuid() != null) {
            validateRefs(new RiskCreateRequest(
                    request.immatriculation() != null ? request.immatriculation() : entity.getImmatriculation(),
                    request.ordre() != null ? request.ordre() : entity.getOrdre(),
                    request.marqueRef(), request.modeleRef(),
                    request.genreUuid() != null ? request.genreUuid() : entity.getGenreUuid(),
                    request.usageUuid() != null ? request.usageUuid() : entity.getUsageUuid(),
                    request.dateMiseEnCirculation() != null ? request.dateMiseEnCirculation() : entity.getDateMiseEnCirculation(),
                    request.energie(), request.numChassis(), request.numMoteur(), request.typeCarrosserie(),
                    request.hasTurbo(), request.hasRemorque(), request.isEnflammable(),
                    request.puissance(), request.tonnage(), request.cylindre(), request.nbPlace(),
                    request.numAttestationUuid() != null ? request.numAttestationUuid() : entity.getNumAttestationUuid(),
                    request.valeurANeuve(), request.valeurVenale(),
                    request.insuredUuid() != null ? request.insuredUuid() : entity.getInsuredUuid(),
                    request.nomConducteur() != null ? request.nomConducteur() : entity.getNomConducteur(),
                    request.sexeConducteur() != null ? request.sexeConducteur() : (entity.getSexeConducteur() != null ? entity.getSexeConducteur().name() : null),
                    request.dateNaissanceConducteur() != null ? request.dateNaissanceConducteur() : entity.getDateNaissanceConducteur(),
                    request.typePermis() != null ? request.typePermis() : (entity.getTypePermis() != null ? entity.getTypePermis().name() : null),
                    request.numPermis() != null ? request.numPermis() : entity.getNumPermis(),
                    request.dateDelivrancePermis() != null ? request.dateDelivrancePermis() : entity.getDateDelivrancePermis(),
                    request.lieuDelivrancePermis() != null ? request.lieuDelivrancePermis() : entity.getLieuDelivrancePermis(),
                    request.delegationCredit() != null ? request.delegationCredit() : entity.getDelegationCredit(),
                    request.zone() != null ? request.zone() : entity.getZone()
            ));
        }
        RiskMapper.applyUpdates(entity, request);
        if (request.marqueRef() != null) entity.setMarque(resolveBrandLabel(request.marqueRef()));
        if (request.modeleRef() != null) entity.setModele(resolveModelLabel(entity.getMarque(), request.modeleRef()));
        return RiskMapper.toResponse(entity);
    }

    @Override
    public void delete(UUID uuid) {
        Risk entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
        repository.delete(entity);
    }

    @Override
    public RiskViewResponse getView(UUID uuid) {
        Risk e = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Risk not found: " + uuid));
        var genre = e.getGenreUuid() == null ? null : settingsClient.getGenre(e.getGenreUuid()).orElse(null);
        var usage = e.getUsageUuid() == null ? null : settingsClient.getUsage(e.getUsageUuid()).orElse(null);
        var att = e.getNumAttestationUuid() == null ? null : attestationClient.get(e.getNumAttestationUuid()).orElse(null);

        RiskViewResponse.RefDto genreDto = genre == null ? null : new RiskViewResponse.RefDto(genre.uuid(), genre.code(), genre.libelle());
        RiskViewResponse.RefDto usageDto = usage == null ? null : new RiskViewResponse.RefDto(usage.uuid(), usage.code(), usage.libelle());
        RiskViewResponse.AttestationDto attDto = att == null ? null : new RiskViewResponse.AttestationDto(att.uuid(), att.numero(), att.statut());

        return new RiskViewResponse(
                e.getUuid(), e.getImmatriculation(), e.getOrdre(),e.getMarque(), e.getModele(),
                genreDto, usageDto,
                e.getDateMiseEnCirculation(), e.getEnergie() != null ? e.getEnergie().name() : null,
                e.getNumChassis(), e.getNumMoteur(), e.getTypeCarrosserie() != null ? e.getTypeCarrosserie().name() : null,
                e.getHasTurbo(), e.getHasRemorque(), e.getIsEnflammable(),
                e.getPuissance(), e.getTonnage(), e.getCylindre(), e.getNbPlace(),
                attDto,
                e.getValeurANeuve(), e.getValeurVenale(),
                e.getInsuredUuid(),
                e.getNomConducteur(),
                e.getSexeConducteur() != null ? e.getSexeConducteur().name() : null,
                e.getDateNaissanceConducteur(),
                e.getTypePermis() != null ? e.getTypePermis().name() : null,
                e.getNumPermis(),
                e.getDateDelivrancePermis(),
                e.getLieuDelivrancePermis(),
                e.getDelegationCredit(),
                e.getZone(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }

    private void validateRefs(RiskCreateRequest r) {
//        if (r.genreUuid() != null && settingsClient.getGenre(r.genreUuid()).isEmpty())
//            throw new BusinessException("Genre introuvable: " + r.genreUuid());
//        if (r.usageUuid() != null && settingsClient.getUsage(r.usageUuid()).isEmpty())
//            throw new BusinessException("Usage introuvable: " + r.usageUuid());
//        if (r.numAttestationUuid() != null && attestationClient.get(r.numAttestationUuid()).isEmpty())
//            throw new BusinessException("Attestation introuvable: " + r.numAttestationUuid());
        if (r.nbPlace() != null && r.nbPlace() <= 0)
            throw new BusinessException("nbPlace doit être > 0");
    }

    private String resolveBrandLabel(String ref) {
        if (ref == null || ref.isBlank()) return null;
        var brands = settingsClient.searchBrands(ref);
        return brands.stream().findFirst().map(SettingsClient.BrandDto::libelle)
                .orElseThrow(() -> new BusinessException("Marque inconnue: " + ref));
    }

    private String resolveModelLabel(String brandLabel, String ref) {
        if (ref == null || ref.isBlank()) return null;
        // If brand is known, try scoped model search, else fallback
        var models = settingsClient.searchModels(brandLabel != null ? brandLabel : "", ref);
        return models.stream().findFirst().map(SettingsClient.ModelDto::libelle)
                .orElseThrow(() -> new BusinessException("Modèle inconnu: " + ref));
    }
}

