package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsuranceinsured.enums.EmploymentStatus;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.MaritalStatus;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record InsuredResponse(

        UUID id,

        // Personal information
        String lastName,
        String firstName,
        LocalDate birthDate,
        Gender gender,
        MaritalStatus maritalStatus,
        String address,
        String city,
        String postalCode,
        String email,
        String phone,

        // Administrative information
        String licenseNumber,
        LocalDate licenseIssueDate,
        LicenseCategory licenseCategory,
        LocalDate registrationDate,
        String profession,
        EmploymentStatus employmentStatus

) {}
