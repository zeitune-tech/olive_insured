package sn.zeitune.oliveinsuranceinsured.app.mappers;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.RiskUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.RiskResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;
import sn.zeitune.oliveinsuranceinsured.enums.Energie;
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
}

