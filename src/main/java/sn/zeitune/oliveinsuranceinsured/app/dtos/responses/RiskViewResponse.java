package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import sn.zeitune.oliveinsuranceinsured.enums.Energie;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.TypeCarrosserie;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RiskViewResponse(
        Long id,
        UUID uuid,
        Boolean isFleetMember,
        UUID parentFleetPoliceUuid,
        Integer numAvenant,
        String immatriculation,
        Integer ordre,
        String marque,
        String modele,
        UUID productUuid,

        // Nested insured information
        InsuredResponse insured,

        // Driver entries list
        List<RiskDriverEntryResponse> driverEntries,

        String driverName,
        Gender driverGender,
        LocalDate driverBirthDate,
        LicenseCategory licenseType,
        String licenseNumber,
        LocalDate licenseIssueDate,
        String licenseIssuePlace,
        Boolean creditDelegation,
        String zone,
        UUID vehicleTypeUuid,
        UUID genreUuid,
        UUID usageUuid,
        LocalDate firstRegistrationDate,
        Energie energie,
        String chassisNumber,
        String engineNumber,
        TypeCarrosserie bodyType,
        Boolean hasTurbo,
        Boolean hasTrailer,
        Boolean isFlammable,
        BigDecimal power,
        BigDecimal tonnage,
        BigDecimal cylinder,
        Integer seatCount,
        UUID attestationNumberUuid,
        UUID formulaPTUuid,
        Integer nbPersonsTransported,
        Integer maxSpeed,
        BigDecimal newValue,
        BigDecimal marketValue,

        // Primes garanties list
        List<PrimeGarantieResponse> primesGaranties,

        Instant createdAt,
        Instant updatedAt
) {
}