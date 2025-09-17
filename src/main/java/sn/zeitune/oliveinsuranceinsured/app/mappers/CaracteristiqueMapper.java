package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.Caracteristique;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.CaracteristiqueListe;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.CaracteristiqueNumerique;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.CaracteristiqueTexte;

import java.util.ArrayList;

@Component
public class CaracteristiqueMapper {

    public Caracteristique toEntity(CaracteristiqueCreateRequest request) {
        if (request == null) {
            return null;
        }

        // Determine concrete class based on typeCaracteristique
        String type = request.typeCaracteristique();
        if (type == null) {
            throw new IllegalArgumentException("typeCaracteristique ne doit pas être null");
        }
        String normalized = type.trim().toUpperCase();

        Caracteristique entity = switch (normalized) {
            case "TEXTE", "TEXT" -> new CaracteristiqueTexte();
            case "NUMERIQUE", "NUMBER" -> new CaracteristiqueNumerique();
            case "LISTE", "SELECT", "MULTI_SELECT" -> new CaracteristiqueListe();
            default ->
                    throw new IllegalArgumentException("Type de caractéristique inconnu: " + request.typeCaracteristique());
        };

        // Map common fields
        entity.setNom(request.nom());
        entity.setDescription(request.description());
        if (request.obligatoire() != null) {
            entity.setObligatoire(request.obligatoire());
        }
        if (request.ordreAffichage() != null) {
            entity.setOrdreAffichage(request.ordreAffichage());
        }
        if (request.actif() != null) {
            entity.setActif(request.actif());
        }
        entity.setCompanyUuid(request.companyUuid());
        if (request.warrantiesUuids() != null) {
            entity.setWarrantiesUuids(new ArrayList<>(request.warrantiesUuids()));
        }

        return entity;
    }

    /**
     * Converts Caracteristique entity to CaracteristiqueResponse
     * Note: This mapper only handles reading operations since Caracteristique is abstract
     * Entity creation should be handled by concrete subclass mappers
     */
    public CaracteristiqueResponse toResponse(Caracteristique entity) {
        if (entity == null) {
            return null;
        }

        return new CaracteristiqueResponse(
                entity.getUuid(),
                entity.getNom(),
                entity.getDescription(),
                entity.getObligatoire(),
                entity.getOrdreAffichage(),
                entity.getActif(),
                entity.getCompanyUuid(),
                entity.getWarrantiesUuids(),
                getDiscriminatorValue(entity),
                entity.getTypeAttente()
        );
    }

    /**
     * Updates existing Caracteristique entity with data from CaracteristiqueUpdateRequest
     * Only updates non-null fields from the request
     * Note: This handles common fields; subclasses may need additional logic
     */
    public void updateEntity(Caracteristique entity, CaracteristiqueUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.nom() != null) {
            entity.setNom(request.nom());
        }
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
        if (request.obligatoire() != null) {
            entity.setObligatoire(request.obligatoire());
        }
        if (request.ordreAffichage() != null) {
            entity.setOrdreAffichage(request.ordreAffichage());
        }
        if (request.actif() != null) {
            entity.setActif(request.actif());
        }
        if (request.companyUuid() != null) {
            entity.setCompanyUuid(request.companyUuid());
        }
        if (request.warrantiesUuids() != null) {
            entity.setWarrantiesUuids(new ArrayList<>(request.warrantiesUuids()));
        }
    }

    /**
     * Helper method to extract discriminator value from entity class
     */
    private String getDiscriminatorValue(Caracteristique entity) {
        // Get the @DiscriminatorValue annotation from the concrete class
        Class<?> entityClass = entity.getClass();

        // Check for @DiscriminatorValue annotation
        jakarta.persistence.DiscriminatorValue discriminatorValue =
                entityClass.getAnnotation(jakarta.persistence.DiscriminatorValue.class);

        if (discriminatorValue != null) {
            return discriminatorValue.value();
        }

        // Fallback to simple class name if no annotation found
        return entityClass.getSimpleName();
    }
}