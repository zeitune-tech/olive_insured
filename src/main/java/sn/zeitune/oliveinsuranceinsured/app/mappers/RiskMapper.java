package sn.zeitune.oliveinsuranceinsured.app.mappers;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.enums.Energie;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.TypeCarrosserie;

public class RiskMapper {
    public static Risk toEntity(RiskCreateRequest r) {
        return Risk.builder()
                .immatriculation(r.immatriculation())
                .ordre(r.ordre())
                .marque(null)
                .modele(null)
                .genreUuid(r.genreUuid())
                .usageUuid(r.usageUuid())
                .dateMiseEnCirculation(r.dateMiseEnCirculation())
                .energie(parseEnergie(r.energie()))
                .numChassis(r.numChassis())
                .numMoteur(r.numMoteur())
                .typeCarrosserie(parseTypeCarrosserie(r.typeCarrosserie()))
                .hasTurbo(r.hasTurbo())
                .hasRemorque(r.hasRemorque())
                .isEnflammable(r.isEnflammable())
                .puissance(r.puissance())
                .tonnage(r.tonnage())
                .cylindre(r.cylindre())
                .nbPlace(r.nbPlace())
                .numAttestationUuid(r.numAttestationUuid())
                .valeurANeuve(r.valeurANeuve())
                .valeurVenale(r.valeurVenale())
                .insuredUuid(r.insuredUuid())
                .nomConducteur(r.nomConducteur())
                .sexeConducteur(parseGender(r.sexeConducteur()))
                .dateNaissanceConducteur(r.dateNaissanceConducteur())
                .typePermis(parseLicenseCategory(r.typePermis()))
                .numPermis(r.numPermis())
                .dateDelivrancePermis(r.dateDelivrancePermis())
                .lieuDelivrancePermis(r.lieuDelivrancePermis())
                .delegationCredit(r.delegationCredit())
                .zone(r.zone())
                .build();
    }

    public static void applyUpdates(Risk entity, RiskUpdateRequest r) {
        if (r.immatriculation() != null) entity.setImmatriculation(r.immatriculation());
        if (r.ordre() != null) entity.setOrdre(r.ordre());
        if (r.genreUuid() != null) entity.setGenreUuid(r.genreUuid());
        if (r.usageUuid() != null) entity.setUsageUuid(r.usageUuid());
        if (r.dateMiseEnCirculation() != null) entity.setDateMiseEnCirculation(r.dateMiseEnCirculation());
        if (r.energie() != null) entity.setEnergie(parseEnergie(r.energie()));
        if (r.numChassis() != null) entity.setNumChassis(r.numChassis());
        if (r.numMoteur() != null) entity.setNumMoteur(r.numMoteur());
        if (r.typeCarrosserie() != null) entity.setTypeCarrosserie(parseTypeCarrosserie(r.typeCarrosserie()));
        if (r.hasTurbo() != null) entity.setHasTurbo(r.hasTurbo());
        if (r.hasRemorque() != null) entity.setHasRemorque(r.hasRemorque());
        if (r.isEnflammable() != null) entity.setIsEnflammable(r.isEnflammable());
        if (r.puissance() != null) entity.setPuissance(r.puissance());
        if (r.tonnage() != null) entity.setTonnage(r.tonnage());
        if (r.cylindre() != null) entity.setCylindre(r.cylindre());
        if (r.nbPlace() != null) entity.setNbPlace(r.nbPlace());
        if (r.numAttestationUuid() != null) entity.setNumAttestationUuid(r.numAttestationUuid());
        if (r.valeurANeuve() != null) entity.setValeurANeuve(r.valeurANeuve());
        if (r.valeurVenale() != null) entity.setValeurVenale(r.valeurVenale());
        if (r.insuredUuid() != null) entity.setInsuredUuid(r.insuredUuid());
        if (r.nomConducteur() != null) entity.setNomConducteur(r.nomConducteur());
        if (r.sexeConducteur() != null) entity.setSexeConducteur(parseGender(r.sexeConducteur()));
        if (r.dateNaissanceConducteur() != null) entity.setDateNaissanceConducteur(r.dateNaissanceConducteur());
        if (r.typePermis() != null) entity.setTypePermis(parseLicenseCategory(r.typePermis()));
        if (r.numPermis() != null) entity.setNumPermis(r.numPermis());
        if (r.dateDelivrancePermis() != null) entity.setDateDelivrancePermis(r.dateDelivrancePermis());
        if (r.lieuDelivrancePermis() != null) entity.setLieuDelivrancePermis(r.lieuDelivrancePermis());
        if (r.delegationCredit() != null) entity.setDelegationCredit(r.delegationCredit());
        if (r.zone() != null) entity.setZone(r.zone());
    }

    public static RiskResponse toResponse(Risk e) {
        return new RiskResponse(
                e.getUuid(),
                e.getImmatriculation(),
                e.getOrdre(),
                e.getMarque(),
                e.getModele(),
                e.getGenreUuid(),
                e.getUsageUuid(),
                e.getDateMiseEnCirculation(),
                e.getEnergie() != null ? e.getEnergie().name() : null,
                e.getNumChassis(),
                e.getNumMoteur(),
                e.getTypeCarrosserie() != null ? e.getTypeCarrosserie().name() : null,
                e.getHasTurbo(),
                e.getHasRemorque(),
                e.getIsEnflammable(),
                e.getPuissance(),
                e.getTonnage(),
                e.getCylindre(),
                e.getNbPlace(),
                e.getNumAttestationUuid(),
                e.getValeurANeuve(),
                e.getValeurVenale(),
                e.getInsuredUuid(),
                e.getNomConducteur(),
                e.getSexeConducteur() != null ? e.getSexeConducteur().name() : null,
                e.getDateNaissanceConducteur(),
                e.getTypePermis() != null ? e.getTypePermis().name() : null,
                e.getNumPermis(),
                e.getDateDelivrancePermis(),
                e.getLieuDelivrancePermis(),
                e.getDelegationCredit(),
                e.getZone(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private static Energie parseEnergie(String s) {
        if (s == null) return null;
        return Energie.valueOf(s.trim().toUpperCase());
    }

    private static TypeCarrosserie parseTypeCarrosserie(String s) {
        if (s == null) return null;
        return TypeCarrosserie.valueOf(s.trim().toUpperCase());
    }

    private static Gender parseGender(String s) {
        if (s == null) return null;
        return Gender.valueOf(s.trim().toUpperCase());
    }

    private static LicenseCategory parseLicenseCategory(String s) {
        if (s == null) return null;
        return LicenseCategory.valueOf(s.trim().toUpperCase());
    }
}

