package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record InsuredCreateRequest(
        String civilite,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotNull LocalDate dateNaissance,
        String typePiece,
        String numeroPiece,
        String adresse,
        String villeRef,
        @Email String email,
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid E.164 format") String phoneFixe,
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid E.164 format") String phoneMobile,
        String professionRef,
        String activiteRef,
        String qualiteRef,
        String nomEntreprise,
        String patente,
        String registreDeCommerce
) {}
