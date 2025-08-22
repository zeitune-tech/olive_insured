package sn.zeitune.oliveinsuranceinsured.app.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.InsuredService;

import java.util.UUID;

@RestController
@RequestMapping("/app/insured")
@RequiredArgsConstructor
public class InsuredController {

    private final InsuredService service;

    @PostMapping
    public ResponseEntity<InsuredResponse> create(@Valid @RequestBody InsuredCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InsuredResponse> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.get(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<InsuredResponse>> search(
            @RequestParam(name = "query", required = false) String query,
            Pageable pageable) {
        return ResponseEntity.ok(service.search(query, pageable));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<InsuredResponse> update(@PathVariable UUID uuid,
                                                              @Valid @RequestBody InsuredUpdateRequest req) {
        return ResponseEntity.ok(service.update(uuid, req));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<InsuredResponse> patch(@PathVariable UUID uuid,
                                                 @RequestBody InsuredUpdateRequest req) {
        return ResponseEntity.ok(service.update(uuid, req));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

