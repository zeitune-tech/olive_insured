package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.OptionCaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.*;

import java.util.ArrayList;
import java.util.List;

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
            case "TEXTE", "TEXT" -> {
                CaracteristiqueTexte texte = new CaracteristiqueTexte();
                texte.setLongueurMin(request.longueurMin());
                texte.setLongueurMax(request.longueurMax());
                texte.setRegexValidation(request.regexValidation());
                yield texte;
            }
            case "NUMERIQUE", "NUMBER" -> {
                CaracteristiqueNumerique numerique = new CaracteristiqueNumerique();
                numerique.setValeurMin(request.valeurMin());
                numerique.setValeurMax(request.valeurMax());
                numerique.setNombreDecimales(request.nombreDecimales());
                yield numerique;
            }
            case "LISTE", "SELECT", "MULTI_SELECT" -> {
                CaracteristiqueListe liste = new CaracteristiqueListe();
                liste.setSelectionMultiple(request.selectionMultiple());
                if (request.options() != null) {
                    List<OptionCaracteristique> options = request.options().stream()
                        .map(optReq -> {
                            OptionCaracteristique opt = new OptionCaracteristique();
                            opt.setValeur(optReq.valeur());
                            opt.setLibelle(optReq.libelle());
                            opt.setOrdre(optReq.ordre());
                            opt.setActif(true);
                            opt.setCaracteristique(liste);
                            return opt;
                        })
                        .toList();
                    liste.setOptions(new ArrayList<>(options));
                }
                yield liste;
            }
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

        // Propriétés spécifiques selon le type
        var valeurMin = entity instanceof CaracteristiqueNumerique num ? num.getValeurMin() : null;
        var valeurMax = entity instanceof CaracteristiqueNumerique num ? num.getValeurMax() : null;
        var nombreDecimales = entity instanceof CaracteristiqueNumerique num ? num.getNombreDecimales() : null;

        var longueurMin = entity instanceof CaracteristiqueTexte txt ? txt.getLongueurMin() : null;
        var longueurMax = entity instanceof CaracteristiqueTexte txt ? txt.getLongueurMax() : null;
        var regexValidation = entity instanceof CaracteristiqueTexte txt ? txt.getRegexValidation() : null;

        var selectionMultiple = entity instanceof CaracteristiqueListe liste ? liste.getSelectionMultiple() : null;
        var options = entity instanceof CaracteristiqueListe liste ?
            liste.getOptions().stream()
                .map(opt -> new OptionCaracteristiqueResponse(
                    opt.getValeur(),
                    opt.getLibelle(),
                    opt.getOrdre(),
                    opt.getActif()
                ))
                .toList() : null;

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
                entity.getTypeAttente(),
                valeurMin,
                valeurMax,
                nombreDecimales,
                longueurMin,
                longueurMax,
                regexValidation,
                selectionMultiple,
                options
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

        // Update common fields
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

        // Update specific fields based on entity type
        if (entity instanceof CaracteristiqueNumerique numerique) {
            if (request.valeurMin() != null) {
                numerique.setValeurMin(request.valeurMin());
            }
            if (request.valeurMax() != null) {
                numerique.setValeurMax(request.valeurMax());
            }
            if (request.nombreDecimales() != null) {
                numerique.setNombreDecimales(request.nombreDecimales());
            }
        }

        if (entity instanceof CaracteristiqueTexte texte) {
            if (request.longueurMin() != null) {
                texte.setLongueurMin(request.longueurMin());
            }
            if (request.longueurMax() != null) {
                texte.setLongueurMax(request.longueurMax());
            }
            if (request.regexValidation() != null) {
                texte.setRegexValidation(request.regexValidation());
            }
        }

        if (entity instanceof CaracteristiqueListe liste) {
            if (request.selectionMultiple() != null) {
                liste.setSelectionMultiple(request.selectionMultiple());
            }
            if (request.options() != null) {
                // Clear existing options and set new ones
                liste.getOptions().clear();
                List<OptionCaracteristique> newOptions = request.options().stream()
                    .map(optReq -> {
                        OptionCaracteristique opt = new OptionCaracteristique();
                        opt.setValeur(optReq.valeur());
                        opt.setLibelle(optReq.libelle());
                        opt.setOrdre(optReq.ordre());
                        opt.setActif(true);
                        opt.setCaracteristique(liste);
                        return opt;
                    })
                    .toList();
                liste.getOptions().addAll(newOptions);
            }
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