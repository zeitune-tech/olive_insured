package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskDriverEntryCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskDriverEntryResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskDriverEntryUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.entities.RiskDriverEntry;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;

@Component
public class RiskDriverEntryMapper {

    @Autowired
    private RiskRepository riskRepository;

    /**
     * Converts RiskDriverEntryCreateRequest to RiskDriverEntry entity using builder pattern
     */
    public RiskDriverEntry toEntity(RiskDriverEntryCreateRequest request) {
        if (request == null) {
            return null;
        }

        // Fetch the Risk entity by ID
        Risk risk = riskRepository.findById(request.riskId())
                .orElseThrow(() -> new IllegalArgumentException("Risk not found with ID: " + request.riskId()));

        return RiskDriverEntry.builder()
                .risk(risk)
                .dateEntree(request.dateEntree())
                .dateRetrait(request.dateRetrait())
                .avenantEntree(request.avenantEntree())
                .avenantRetrait(request.avenantRetrait())
                .build();
    }

    /**
     * Converts RiskDriverEntry entity to RiskDriverEntryResponse
     */
    public RiskDriverEntryResponse toResponse(RiskDriverEntry entity) {
        if (entity == null) {
            return null;
        }

        return new RiskDriverEntryResponse(
                entity.getId(),
                entity.getUuid(),
                entity.getRisk() != null ? entity.getRisk().getId() : null,
                entity.getDateEntree(),
                entity.getDateRetrait(),
                entity.getAvenantEntree(),
                entity.getAvenantRetrait(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Updates existing RiskDriverEntry entity with data from RiskDriverEntryUpdateRequest
     * Only updates non-null fields from the request
     */
    public void updateEntity(RiskDriverEntry entity, RiskDriverEntryUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.riskId() != null) {
            Risk risk = riskRepository.findById(request.riskId())
                    .orElseThrow(() -> new IllegalArgumentException("Risk not found with ID: " + request.riskId()));
            entity.setRisk(risk);
        }
        if (request.dateEntree() != null) {
            entity.setDateEntree(request.dateEntree());
        }
        if (request.dateRetrait() != null) {
            entity.setDateRetrait(request.dateRetrait());
        }
        if (request.avenantEntree() != null) {
            entity.setAvenantEntree(request.avenantEntree());
        }
        if (request.avenantRetrait() != null) {
            entity.setAvenantRetrait(request.avenantRetrait());
        }
    }

    /**
     * Alternative method to create entity without fetching Risk from database
     * Useful when the Risk entity is already available in the context
     */
    public RiskDriverEntry toEntityWithRisk(RiskDriverEntryCreateRequest request, Risk risk) {
        if (request == null) {
            return null;
        }

        return RiskDriverEntry.builder()
                .risk(risk)
                .dateEntree(request.dateEntree())
                .dateRetrait(request.dateRetrait())
                .avenantEntree(request.avenantEntree())
                .avenantRetrait(request.avenantRetrait())
                .build();
    }

    /**
     * Alternative method to update entity without fetching Risk from database
     * Useful when the Risk entity is already available in the context
     */
    public void updateEntityWithRisk(RiskDriverEntry entity, RiskDriverEntryUpdateRequest request, Risk risk) {
        if (entity == null || request == null) {
            return;
        }

        if (request.riskId() != null && risk != null) {
            entity.setRisk(risk);
        }
        if (request.dateEntree() != null) {
            entity.setDateEntree(request.dateEntree());
        }
        if (request.dateRetrait() != null) {
            entity.setDateRetrait(request.dateRetrait());
        }
        if (request.avenantEntree() != null) {
            entity.setAvenantEntree(request.avenantEntree());
        }
        if (request.avenantRetrait() != null) {
            entity.setAvenantRetrait(request.avenantRetrait());
        }
    }
}