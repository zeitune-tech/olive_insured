package sn.zeitune.oliveinsuranceinsured.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.PrimeGarantieUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.PrimeGarantieResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.PrimeGarantieService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/prime-garanties")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('USER')")
public class PrimeGarantieController {

    private final PrimeGarantieService primeGarantieService;

    @PostMapping
    public ResponseEntity<PrimeGarantieResponse> create(@Valid @RequestBody PrimeGarantieCreateRequest request) {
        log.info("Creating PrimeGarantie for risk: {}", request.riskUuid());
        PrimeGarantieResponse response = primeGarantieService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<PrimeGarantieResponse>> createMultiple(@Valid @RequestBody List<PrimeGarantieCreateRequest> requests) {
        log.info("Creating {} PrimeGaranties", requests.size());
        List<PrimeGarantieResponse> responses = primeGarantieService.createMultiple(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PrimeGarantieResponse> findByUuid(@PathVariable UUID uuid) {
        log.info("Finding PrimeGarantie by UUID: {}", uuid);
        PrimeGarantieResponse response = primeGarantieService.findByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-risk/{riskUuid}")
    public ResponseEntity<List<PrimeGarantieResponse>> findByRiskUuid(@PathVariable UUID riskUuid) {
        log.info("Finding PrimeGaranties for risk: {}", riskUuid);
        List<PrimeGarantieResponse> responses = primeGarantieService.findByRiskUuid(riskUuid);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-police/{policeUuid}")
    public ResponseEntity<List<PrimeGarantieResponse>> findByPoliceUuid(@PathVariable UUID policeUuid) {
        log.info("Finding PrimeGaranties for police: {}", policeUuid);
        List<PrimeGarantieResponse> responses = primeGarantieService.findByPoliceUuid(policeUuid);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<Page<PrimeGarantieResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) UUID policeUuid,
            @RequestParam(required = false) UUID riskUuid,
            @RequestParam(required = false) UUID garantieUuid
    ) {
        log.info("Finding PrimeGaranties - page: {}, size: {}, sort: {} {}", page, size, sortBy, sortDir);

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PrimeGarantieResponse> responses;
        if (policeUuid != null || riskUuid != null || garantieUuid != null) {
            responses = primeGarantieService.findWithFilters(policeUuid, riskUuid, garantieUuid, pageable);
        } else {
            responses = primeGarantieService.findAll(pageable);
        }

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PrimeGarantieResponse> update(
            @PathVariable UUID uuid,
            @Valid @RequestBody PrimeGarantieUpdateRequest request
    ) {
        log.info("Updating PrimeGarantie: {}", uuid);
        PrimeGarantieResponse response = primeGarantieService.update(uuid, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        log.info("Deleting PrimeGarantie: {}", uuid);
        primeGarantieService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}