package sn.zeitune.oliveinsuranceinsured.app.controllers.interservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.CaracteristiqueResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.CaracteristiqueService;

import java.util.List;

@RestController
@RequestMapping("/interservices/caracteristique")
public class CaracteristiqueControllerInterservice {

    private final CaracteristiqueService caracteristiqueService;

    public CaracteristiqueControllerInterservice(CaracteristiqueService caracteristiqueService) {
        this.caracteristiqueService = caracteristiqueService;
    }

    @GetMapping
    public ResponseEntity<List<CaracteristiqueResponse>> getAllCaracteristiques() {
        List<CaracteristiqueResponse> responses = caracteristiqueService.getAllCaracteristiques();
        return ResponseEntity.ok(responses);
    }

}