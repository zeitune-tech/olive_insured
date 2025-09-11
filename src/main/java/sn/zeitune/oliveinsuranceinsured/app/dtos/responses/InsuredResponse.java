package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record InsuredResponse(
        UUID uuid,
        String civilite,
        String nom,
        String prenom,
        LocalDate dateNaissance,
        String typePiece,
        String numeroPiece,
        String adresse,
        String ville,
        String email,
        String phoneFixe,
        String phoneMobile,
        String profession,
        String activite,
        String nomEntreprise,
        String patente,
        String registreDeCommerce,
        Instant createdAt,
        Instant updatedAt
) {}