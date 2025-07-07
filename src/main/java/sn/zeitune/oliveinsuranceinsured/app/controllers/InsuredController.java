package sn.zeitune.oliveinsuranceinsured.app.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.InsuredService;
import sn.zeitune.oliveinsuranceinsured.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/insureds")
@RequiredArgsConstructor
public class InsuredController {

    private final InsuredService insuredService;

    @PostMapping
    public ResponseEntity<InsuredResponse> create(
            @RequestBody @Valid InsuredRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        InsuredResponse response = insuredService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InsuredResponse> getByUuid(@PathVariable UUID uuid) {
        InsuredResponse response = insuredService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InsuredResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<InsuredResponse> responses = insuredService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        insuredService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}