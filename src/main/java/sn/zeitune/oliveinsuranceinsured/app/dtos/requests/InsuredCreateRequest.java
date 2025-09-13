package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import sn.zeitune.oliveinsuranceinsured.enums.Civilite;
import sn.zeitune.oliveinsuranceinsured.enums.TypePiece;

import java.time.LocalDate;

public record InsuredCreateRequest(
        @NotNull(message = "Civility is required")
        Civilite civility,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        TypePiece idDocumentType,

        @Size(max = 50, message = "ID document number must not exceed 50 characters")
        String idDocumentNumber,

        @Size(max = 255, message = "Address must not exceed 255 characters")
        String address,

        @Size(max = 100, message = "City must not exceed 100 characters")
        String city,

        @Size(max = 100, message = "Profession must not exceed 100 characters")
        String profession,

        @Size(max = 100, message = "Activity must not exceed 100 characters")
        String activity,

        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @Pattern(regexp = "^[+]?[0-9\\s\\-()]*$", message = "Invalid landline phone format")
        @Size(max = 20, message = "Landline phone must not exceed 20 characters")
        String landlinePhone,

        @Pattern(regexp = "^[+]?[0-9\\s\\-()]*$", message = "Invalid mobile phone format")
        @Size(max = 20, message = "Mobile phone must not exceed 20 characters")
        String mobilePhone,

        @Size(max = 150, message = "Company name must not exceed 150 characters")
        String companyName,

        @Size(max = 50, message = "Business license must not exceed 50 characters")
        String businessLicense,

        @Size(max = 50, message = "Trade register must not exceed 50 characters")
        String tradeRegister
) {
}