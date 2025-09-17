package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import sn.zeitune.oliveinsuranceinsured.enums.Energie;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.TypeCarrosserie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RiskCreateRequest(
        Boolean isFleetMember,

        UUID parentFleetPoliceUuid,

        Integer numAvenant,

        @NotBlank(message = "Immatriculation is required")
        @Size(max = 20, message = "Immatriculation must not exceed 20 characters")
        String immatriculation,

        Integer ordre,

        @Size(max = 100, message = "Marque must not exceed 100 characters")
        String marque,

        @Size(max = 100, message = "Modele must not exceed 100 characters")
        String modele,

        @NotNull(message = "Product UUID is required")
        UUID productUuid,

        @Size(max = 100, message = "Insured first name must not exceed 100 characters")
        String insuredFirstName,

        @Size(max = 100, message = "Insured last name must not exceed 100 characters")
        String insuredLastName,

        @Size(max = 200, message = "Insured address must not exceed 200 characters")
        String insuredAddress,

        @Size(max = 20, message = "Insured phone must not exceed 20 characters")
        String insuredPhone,

        @Valid
        List<ValeurCaracteristiqueRiskCreateRequest> attributes,

        @Size(max = 150, message = "Driver name must not exceed 150 characters")
        String driverName,

        Gender driverGender,

        @Past(message = "Driver birth date must be in the past")
        LocalDate driverBirthDate,

        LicenseCategory licenseType,

        @Size(max = 50, message = "License number must not exceed 50 characters")
        String licenseNumber,

        LocalDate licenseIssueDate,

        @Size(max = 100, message = "License issue place must not exceed 100 characters")
        String licenseIssuePlace,

        Boolean creditDelegation,

        @Size(max = 100, message = "Zone must not exceed 100 characters")
        String zone,

        UUID vehicleTypeUuid,

        UUID genreUuid,
        UUID usageUuid,

        LocalDate firstRegistrationDate,

        Energie energie,

        @Size(max = 50, message = "Chassis number must not exceed 50 characters")
        String chassisNumber,

        @Size(max = 50, message = "Engine number must not exceed 50 characters")
        String engineNumber,

        TypeCarrosserie bodyType,

        Boolean hasTurbo,

        Boolean hasTrailer,

        Boolean isFlammable,

        @DecimalMin(value = "0.0", message = "Power must be positive")
        BigDecimal power,

        @DecimalMin(value = "0.0", message = "Tonnage must be positive")
        BigDecimal tonnage,

        @DecimalMin(value = "0.0", message = "Cylinder must be positive")
        BigDecimal cylinder,

        @Min(value = 1, message = "Seat count must be at least 1")
        Integer seatCount,

        UUID attestationNumberUuid,

        UUID formulaPTUuid,

        @Min(value = 1, message = "Number of persons transported must be at least 1")
        Integer nbPersonsTransported,

        @Min(value = 1, message = "Max speed must be positive")
        Integer maxSpeed,

        @DecimalMin(value = "0.0", message = "New value must be positive")
        BigDecimal newValue,

        @DecimalMin(value = "0.0", message = "Market value must be positive")
        BigDecimal marketValue
) {
}