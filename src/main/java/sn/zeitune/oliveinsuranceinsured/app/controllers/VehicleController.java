package sn.zeitune.oliveinsuranceinsured.app.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.VehicleRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.VehicleResponse;
import sn.zeitune.oliveinsuranceinsured.app.services.VehicleService;
import sn.zeitune.oliveinsuranceinsured.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(
            @RequestBody @Valid VehicleRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        VehicleResponse response = vehicleService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<VehicleResponse> getByUuid(@PathVariable UUID uuid) {
        VehicleResponse response = vehicleService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<VehicleResponse> responses = vehicleService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        vehicleService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
