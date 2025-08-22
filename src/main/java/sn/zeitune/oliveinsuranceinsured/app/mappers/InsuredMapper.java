package sn.zeitune.oliveinsuranceinsured.app.mappers;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;
import sn.zeitune.oliveinsuranceinsured.enums.Civilite;
import sn.zeitune.oliveinsuranceinsured.enums.TypePiece;

public class InsuredMapper {
    public static Insured toEntity(InsuredCreateRequest r) {
        return Insured.builder()
                .civilite(parseCivilite(r.civilite()))
                .nom(r.nom())
                .prenom(r.prenom())
                .dateNaissance(r.dateNaissance())
                .typePiece(parseTypePiece(r.typePiece()))
                .numeroPiece(r.numeroPiece())
                .adresse(r.adresse())
                // ville/profession/activite/qualite are resolved and set in service
                .email(r.email())
                .phoneFixe(r.phoneFixe())
                .phoneMobile(r.phoneMobile())
                .nomEntreprise(r.nomEntreprise())
                .patente(r.patente())
                .registreDeCommerce(r.registreDeCommerce())
                .build();
    }

    public static void applyUpdates(Insured entity, InsuredUpdateRequest r) {
        if (r.civilite() != null) entity.setCivilite(parseCivilite(r.civilite()));
        if (r.nom() != null) entity.setNom(r.nom());
        if (r.prenom() != null) entity.setPrenom(r.prenom());
        if (r.dateNaissance() != null) entity.setDateNaissance(r.dateNaissance());
        if (r.typePiece() != null) entity.setTypePiece(parseTypePiece(r.typePiece()));
        if (r.numeroPiece() != null) entity.setNumeroPiece(r.numeroPiece());
        if (r.adresse() != null) entity.setAdresse(r.adresse());
        if (r.email() != null) entity.setEmail(r.email());
        if (r.phoneFixe() != null) entity.setPhoneFixe(r.phoneFixe());
        if (r.phoneMobile() != null) entity.setPhoneMobile(r.phoneMobile());
        if (r.nomEntreprise() != null) entity.setNomEntreprise(r.nomEntreprise());
        if (r.patente() != null) entity.setPatente(r.patente());
        if (r.registreDeCommerce() != null) entity.setRegistreDeCommerce(r.registreDeCommerce());
    }

    public static InsuredResponse toResponse(Insured e) {
        return new InsuredResponse(
                e.getUuid(),
                e.getCivilite() != null ? e.getCivilite().name() : null,
                e.getNom(),
                e.getPrenom(),
                e.getDateNaissance(),
                e.getTypePiece() != null ? e.getTypePiece().name() : null,
                e.getNumeroPiece(),
                e.getAdresse(),
                e.getVille(),
                e.getEmail(),
                e.getPhoneFixe(),
                e.getPhoneMobile(),
                e.getProfession(),
                e.getActivite(),
                e.getQualite(),
                e.getNomEntreprise(),
                e.getPatente(),
                e.getRegistreDeCommerce(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private static Civilite parseCivilite(String s) {
        if (s == null) return null;
        return Civilite.valueOf(s.trim().toUpperCase());
    }

    private static TypePiece parseTypePiece(String s) {
        if (s == null) return null;
        return TypePiece.valueOf(s.trim().toUpperCase());
    }
}

