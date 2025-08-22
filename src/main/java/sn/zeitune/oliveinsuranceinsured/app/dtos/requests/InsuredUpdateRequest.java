package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record InsuredUpdateRequest(
        String civilite,
        String nom,
        String prenom,
        LocalDate dateNaissance,
        String typePiece,
        String numeroPiece,
        String adresse,
        String villeRef,
        String email,
        String phoneFixe,
        String phoneMobile,
        String professionRef,
        String activiteRef,
        String qualiteRef,
        String nomEntreprise,
        String patente,
        String registreDeCommerce
) {}