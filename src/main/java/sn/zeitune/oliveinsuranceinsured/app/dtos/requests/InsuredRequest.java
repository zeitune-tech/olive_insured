package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsuranceinsured.enums.EmploymentStatus;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.MaritalStatus;

import java.time.LocalDate;

@Builder
public record InsuredRequest(

        // Personal information
        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotNull(message = "Birth date must not be null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Gender must not be null")
        Gender gender,

        @NotNull(message = "Marital status must not be null")
        MaritalStatus maritalStatus,

        @NotBlank(message = "Address must not be blank")
        String address,

        @NotBlank(message = "City must not be blank")
        String city,

        @NotBlank(message = "Postal code must not be blank")
        String postalCode,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email must not be blank")
        String email,

        @NotBlank(message = "Phone number must not be blank")
        String phone,

        // Administrative information
        @NotBlank(message = "License number must not be blank")
        String licenseNumber,

        @NotNull(message = "License issue date must not be null")
        @Past(message = "License issue date must be in the past")
        LocalDate licenseIssueDate,

        @NotNull(message = "License category must not be null")
        LicenseCategory licenseCategory,

        @NotNull(message = "Registration date must not be null")
        @PastOrPresent(message = "Registration date cannot be in the future")
        LocalDate registrationDate,

        @NotBlank(message = "Profession must not be blank")
        String profession,

        @NotNull(message = "Employment status must not be null")
        EmploymentStatus employmentStatus
) {}
