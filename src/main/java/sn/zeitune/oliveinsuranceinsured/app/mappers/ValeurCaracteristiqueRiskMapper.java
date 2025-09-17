package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.ValeurCaracteristiqueRiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.ValeurCaracteristiqueRiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.ValeurCaracteristiqueRiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.Caracteristique;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.ValeurCaracteristiqueRisk;
import sn.zeitune.oliveinsuranceinsured.app.repositories.CaracteristiqueRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;

@Component
public class ValeurCaracteristiqueRiskMapper {

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private CaracteristiqueMapper caracteristiqueMapper;

    public ValeurCaracteristiqueRisk toEntity(ValeurCaracteristiqueRiskCreateRequest request) {
        if (request == null) {
            return null;
        }

        Risk risk = riskRepository.findByUuid(request.riskUuid())
                .orElseThrow(() -> new IllegalArgumentException("Risk not found with UUID: " + request.riskUuid()));

        Caracteristique caracteristique = caracteristiqueRepository.findByUuid(request.caracteristiqueUuid())
                .orElseThrow(() -> new IllegalArgumentException("Caracteristique not found with UUID: " + request.caracteristiqueUuid()));

        ValeurCaracteristiqueRisk entity = new ValeurCaracteristiqueRisk();
        entity.setRisk(risk);
        entity.setCaracteristique(caracteristique);
        entity.setValeur(request.valeur());
        entity.setEntiteAssocieeUuid(request.entiteAssocieeUuid());

        return entity;
    }

    public ValeurCaracteristiqueRiskResponse toResponse(ValeurCaracteristiqueRisk entity) {
        if (entity == null) {
            return null;
        }

        return new ValeurCaracteristiqueRiskResponse(
                entity.getRisk() != null ? entity.getRisk().getUuid() : null,
                entity.getCaracteristique() != null ? caracteristiqueMapper.toResponse(entity.getCaracteristique()) : null,
                entity.getValeur(),
                entity.getEntiteAssocieeUuid()
        );
    }

    public void updateEntity(ValeurCaracteristiqueRisk entity, ValeurCaracteristiqueRiskUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.riskUuid() != null) {
            Risk police = riskRepository.findByUuid(request.riskUuid())
                    .orElseThrow(() -> new IllegalArgumentException("Risk not found with ID: " + request.riskUuid()));
            entity.setRisk(police);
        }

        if (request.caracteristiqueUuid() != null) {
            Caracteristique caracteristique = caracteristiqueRepository.findByUuid(request.caracteristiqueUuid())
                    .orElseThrow(() -> new IllegalArgumentException("Caracteristique not found with ID: " + request.caracteristiqueUuid()));
            entity.setCaracteristique(caracteristique);
        }

        if (request.valeur() != null) {
            entity.setValeur(request.valeur());
        }

        if (request.entiteAssocieeUuid() != null) {
            entity.setEntiteAssocieeUuid(request.entiteAssocieeUuid());
        }
    }

    public ValeurCaracteristiqueRisk toEntityWithEntities(
            ValeurCaracteristiqueRiskCreateRequest request,
            Risk police,
            Caracteristique caracteristique) {
        if (request == null) {
            return null;
        }

        ValeurCaracteristiqueRisk entity = new ValeurCaracteristiqueRisk();
        entity.setRisk(police);
        entity.setCaracteristique(caracteristique);
        entity.setValeur(request.valeur());
        entity.setEntiteAssocieeUuid(request.entiteAssocieeUuid());

        return entity;
    }

    public void updateEntityWithEntities(
            ValeurCaracteristiqueRisk entity,
            ValeurCaracteristiqueRiskUpdateRequest request,
            Risk police,
            Caracteristique caracteristique) {
        if (entity == null || request == null) {
            return;
        }

        if (request.riskUuid() != null && police != null) {
            entity.setRisk(police);
        }

        if (request.caracteristiqueUuid() != null && caracteristique != null) {
            entity.setCaracteristique(caracteristique);
        }

        if (request.valeur() != null) {
            entity.setValeur(request.valeur());
        }

        if (request.entiteAssocieeUuid() != null) {
            entity.setEntiteAssocieeUuid(request.entiteAssocieeUuid());
        }
    }
}