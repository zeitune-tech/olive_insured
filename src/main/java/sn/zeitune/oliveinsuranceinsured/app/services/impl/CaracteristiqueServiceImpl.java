package sn.zeitune.oliveinsuranceinsured.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.ValeurCaracteristiqueRiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.ValeurCaracteristiqueRiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.Caracteristique;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.CaracteristiqueListe;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.ValeurCaracteristiqueRisk;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.BusinessException;
import sn.zeitune.oliveinsuranceinsured.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsuranceinsured.app.mappers.CaracteristiqueMapper;
import sn.zeitune.oliveinsuranceinsured.app.mappers.ValeurCaracteristiqueRiskMapper;
import sn.zeitune.oliveinsuranceinsured.app.repositories.CaracteristiqueRepository;
import sn.zeitune.oliveinsuranceinsured.app.repositories.RiskRepository;
import sn.zeitune.oliveinsuranceinsured.app.services.CaracteristiqueService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CaracteristiqueServiceImpl implements CaracteristiqueService {

    private final CaracteristiqueRepository caracteristiqueRepository;
    private final RiskRepository riskRepository;
    private final CaracteristiqueMapper caracteristiqueMapper;
    private final ValeurCaracteristiqueRiskMapper valeurMapper;

    @Override
    @Transactional
    public CaracteristiqueResponse createCaracteristique(CaracteristiqueCreateRequest request) {
        // Use factory to create the appropriate concrete subclass
        Caracteristique caracteristique = caracteristiqueMapper.toEntity(request);
        Caracteristique savedCaracteristique = caracteristiqueRepository.save(caracteristique);
        return caracteristiqueMapper.toResponse(savedCaracteristique);
    }

    @Override
    public CaracteristiqueResponse getCaracteristiqueByUuid(UUID uuid) {
        Caracteristique caracteristique = caracteristiqueRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Caractéristique non trouve avec UUID: " + uuid));
        return caracteristiqueMapper.toResponse(caracteristique);
    }

    @Override
    public List<CaracteristiqueResponse> getAllCaracteristiques() {
        return caracteristiqueRepository.findByActifTrue().stream()
                .map(caracteristiqueMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional
    public CaracteristiqueResponse updateCaracteristique(UUID uuid, CaracteristiqueUpdateRequest request) {
        Caracteristique existingCaracteristique = caracteristiqueRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Caractéristique non trouvée avec UUID: " + uuid));

        // Use mapper to update entity
        caracteristiqueMapper.updateEntity(existingCaracteristique, request);

        // Handle special case for CaracteristiqueListe if needed
        if (existingCaracteristique instanceof CaracteristiqueListe) {
            updateCaracteristiqueListeOptions((CaracteristiqueListe) existingCaracteristique, request);
        }

        Caracteristique updatedCaracteristique = caracteristiqueRepository.save(existingCaracteristique);
        return caracteristiqueMapper.toResponse(updatedCaracteristique);
    }

    private void updateCaracteristiqueListeOptions(CaracteristiqueListe existingListe, CaracteristiqueUpdateRequest request) {
        // Clear existing options to prevent duplication
        // The cascade = CascadeType.ALL will handle the deletion of orphaned options
        existingListe.getOptions().clear();

        // Note: Since CaracteristiqueUpdateRequest doesn't include options data,
        // this method currently only clears existing options.
        // To fully support options update, you would need to either:
        // 1. Extend CaracteristiqueUpdateRequest to include List<OptionCaracteristiqueRequest> options
        // 2. Create a separate endpoint for updating options
        // 3. Use a different DTO that includes options data
    }

    @Override
    @Transactional
    public void deleteCaracteristique(UUID uuid) {
        Caracteristique caracteristique = caracteristiqueRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Caractéristique non trouvée avec UUID: " + uuid));

        // Soft delete - mark as inactive
        caracteristique.setActif(false);
        caracteristiqueRepository.save(caracteristique);
    }

    @Override
    @Transactional
    public ValeurCaracteristiqueRiskResponse ajouterValeurARisk(UUID riskUuid, ValeurCaracteristiqueRiskCreateRequest request) {
        Risk risk = riskRepository.findByUuid(riskUuid)
                .orElseThrow(() -> new NotFoundException("Risk non trouvée avec UUID: " + riskUuid));

        Caracteristique caracteristique = caracteristiqueRepository.findByUuid(request.caracteristiqueUuid())
                .orElseThrow(() -> new NotFoundException("Caractéristique non trouvée avec ID: " + request.caracteristiqueUuid()));

        // Validate company consistency if applicable
//        if (caracteristique.getCompanyUuid() != null && !caracteristique.getCompanyUuid().equals(risk.getCompanyUuid())) {
//            throw new BusinessException("Caractéristique non applicable à la compagnie de la risk");
//        }

        // Validate value
        if (!caracteristique.validerValeur(request.valeur())) {
            throw new BusinessException("La valeur fournie ne respecte pas les contraintes de la caractéristique");
        }

        // Create value using mapper with preloaded entities
        ValeurCaracteristiqueRisk valeur = valeurMapper.toEntityWithEntities(request, risk, caracteristique);
        risk.getAttributes().add(valeur);

        Risk savedRisk = riskRepository.save(risk);

        // Find the saved value
        ValeurCaracteristiqueRisk savedValeur = savedRisk.getAttributes().stream()
                .filter(v -> v.getCaracteristique().getUuid().equals(request.caracteristiqueUuid()) &&
                        java.util.Objects.equals(v.getEntiteAssocieeUuid(), request.entiteAssocieeUuid()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Erreur lors de l'enregistrement de la valeur"));

        return valeurMapper.toResponse(savedValeur);
    }


    @Override
    public List<ValeurCaracteristiqueRiskResponse> getValeursCaracteristiquesRisk(UUID riskUuid) {
        Risk risk = riskRepository.findByUuid(riskUuid)
                .orElseThrow(() -> new NotFoundException("Risk non trouvée avec UUID: " + riskUuid));

        return risk.getAttributes().stream()
                .map(valeurMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void supprimerValeurCaracteristique(UUID riskUuid, UUID caracteristiqueUuid, UUID entiteAssocieeUuid) {
        Risk risk = riskRepository.findByUuid(riskUuid)
                .orElseThrow(() -> new NotFoundException("Risk non trouvée avec UUID: " + riskUuid));

        boolean removed = risk.getAttributes().removeIf(v ->
                v.getCaracteristique().getUuid().equals(caracteristiqueUuid) &&
                        java.util.Objects.equals(v.getEntiteAssocieeUuid(), entiteAssocieeUuid));

        if (!removed) {
            throw new NotFoundException("Valeur de caractéristique non trouvée");
        }

        riskRepository.save(risk);
    }

    @Override
    public boolean validerValeur(UUID caracteristiqueUuid, Object valeur) {
        Caracteristique caracteristique = caracteristiqueRepository.findByUuid(caracteristiqueUuid)
                .orElseThrow(() -> new NotFoundException("Caractéristique non trouvée avec UUID: " + caracteristiqueUuid));

        return caracteristique.validerValeur(valeur);
    }
}