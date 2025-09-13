package sn.zeitune.oliveinsuranceinsured.app.controllers.interservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskViewResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.RiskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/interservices/risks")
@RequiredArgsConstructor
public class RiskInterserviceController {

    private final RiskService service;

    @PostMapping
    public ResponseEntity<RiskResponse> create(@Valid @RequestBody RiskCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RiskResponse> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.get(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<RiskResponse>> list(
            @RequestParam(name = "immatriculation", required = false) String immatriculation,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.searchByImmatriculation(immatriculation, pageable));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RiskResponse> update(@PathVariable UUID uuid,
                                               @Valid @RequestBody RiskUpdateRequest req) {
        return ResponseEntity.ok(service.update(uuid, req));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<RiskResponse> patch(@PathVariable UUID uuid,
                                              @RequestBody RiskUpdateRequest req) {
        return ResponseEntity.ok(service.update(uuid, req));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uuid}/view")
    public ResponseEntity<RiskViewResponse> view(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getView(uuid));
    }

    @PostMapping("/by-uuids")
    public ResponseEntity<List<RiskResponse>> findByUuids(@RequestBody List<UUID> uuids) {
        return ResponseEntity.ok(service.findByUuids(uuids));
    }

    @GetMapping("/by-insured/{insuredUuid}")
    public ResponseEntity<List<RiskResponse>> findByInsuredUuid(@PathVariable UUID insuredUuid) {
        return ResponseEntity.ok(service.findByInsuredUuid(insuredUuid));
    }
}
