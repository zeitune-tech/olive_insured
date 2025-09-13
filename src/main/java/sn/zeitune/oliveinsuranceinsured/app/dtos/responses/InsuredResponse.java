package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import sn.zeitune.oliveinsuranceinsured.enums.Civilite;
import sn.zeitune.oliveinsuranceinsured.enums.TypePiece;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record InsuredResponse(
        Long id,
        UUID uuid,
        Civilite civility,
        String lastName,
        String firstName,
        LocalDate birthDate,
        TypePiece idDocumentType,
        String idDocumentNumber,
        String address,
        String city,
        String profession,
        String activity,
        String email,
        String landlinePhone,
        String mobilePhone,
        String companyName,
        String businessLicense,
        String tradeRegister,
        Instant createdAt,
        Instant updatedAt
) {
}