package sn.zeitune.oliveinsuranceinsured.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.CaracteristiqueUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.CaracteristiqueService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/caracteristique")
public class CaracteristiqueController {

    private final CaracteristiqueService caracteristiqueService;

    public CaracteristiqueController(CaracteristiqueService caracteristiqueService) {
        this.caracteristiqueService = caracteristiqueService;
    }

    @PostMapping
    public ResponseEntity<CaracteristiqueResponse> createCaracteristique(@RequestBody CaracteristiqueCreateRequest request) {
        CaracteristiqueResponse response = caracteristiqueService.createCaracteristique(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CaracteristiqueResponse> getCaracteristiqueByUuid(@PathVariable UUID uuid) {
        CaracteristiqueResponse response = caracteristiqueService.getCaracteristiqueByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CaracteristiqueResponse>> getAllCaracteristiques() {
        List<CaracteristiqueResponse> responses = caracteristiqueService.getAllCaracteristiques();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CaracteristiqueResponse> updateCaracteristique(@PathVariable UUID uuid, @RequestBody CaracteristiqueUpdateRequest request) {
        CaracteristiqueResponse response = caracteristiqueService.updateCaracteristique(uuid, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCaracteristique(@PathVariable UUID uuid) {
        caracteristiqueService.deleteCaracteristique(uuid);
        return ResponseEntity.noContent().build();
    }
}