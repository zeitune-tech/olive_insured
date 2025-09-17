package sn.zeitune.oliveinsuranceinsured.app.services;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.ValeurCaracteristiqueRiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.ValeurCaracteristiqueRiskResponse;

import java.util.List;
import java.util.UUID;

public interface CaracteristiqueService {
    CaracteristiqueResponse createCaracteristique(CaracteristiqueCreateRequest request);
    CaracteristiqueResponse getCaracteristiqueByUuid(UUID uuid);
    List<CaracteristiqueResponse> getAllCaracteristiques();
    CaracteristiqueResponse updateCaracteristique(UUID uuid, CaracteristiqueUpdateRequest request);
    void deleteCaracteristique(UUID uuid);
    
    ValeurCaracteristiqueRiskResponse ajouterValeurARisk(UUID policeUuid, ValeurCaracteristiqueRiskCreateRequest request);
    List<ValeurCaracteristiqueRiskResponse> getValeursCaracteristiquesRisk(UUID policeUuid);
    void supprimerValeurCaracteristique(UUID policeUuid, UUID caracteristiqueUuid, UUID entiteAssocieeUuid);
    
    boolean validerValeur(UUID caracteristiqueUuid, Object valeur);
}