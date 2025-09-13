package sn.zeitune.oliveinsuranceinsured.app.controllers.interservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.PrimeGarantieService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/interservices/prime-garanties")
@RequiredArgsConstructor
@Slf4j
public class PrimeGarantieInterserviceController {

    private final PrimeGarantieService primeGarantieService;

    @GetMapping("/{uuid}")
    public ResponseEntity<PrimeGarantieResponse> findByUuid(@PathVariable UUID uuid) {
        log.debug("Inter-service: Finding PrimeGarantie by UUID: {}", uuid);
        PrimeGarantieResponse response = primeGarantieService.findByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-risk/{riskUuid}")
    public ResponseEntity<List<PrimeGarantieResponse>> findByRiskUuid(@PathVariable UUID riskUuid) {
        log.debug("Inter-service: Finding PrimeGaranties for risk: {}", riskUuid);
        List<PrimeGarantieResponse> responses = primeGarantieService.findByRiskUuid(riskUuid);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-police/{policeUuid}")
    public ResponseEntity<List<PrimeGarantieResponse>> findByPoliceUuid(@PathVariable UUID policeUuid) {
        log.debug("Inter-service: Finding PrimeGaranties for police: {}", policeUuid);
        List<PrimeGarantieResponse> responses = primeGarantieService.findByPoliceUuid(policeUuid);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/exists/{uuid}")
    public ResponseEntity<Boolean> existsByUuid(@PathVariable UUID uuid) {
        log.debug("Inter-service: Checking if PrimeGarantie exists: {}", uuid);
        try {
            primeGarantieService.findByUuid(uuid);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}