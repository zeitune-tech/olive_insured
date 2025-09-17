package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TEXTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristiqueTexte extends Caracteristique {

    @Column(name = "longueur_min")
    private Integer longueurMin;

    @Column(name = "longueur_max")
    private Integer longueurMax;

    @Column(name = "regex_validation")
    private String regexValidation;

    @Override
    public boolean validerValeur(Object valeur) {
        if (valeur == null) {
            return !Boolean.TRUE.equals(getObligatoire());
        }
        
        if (!(valeur instanceof String)) {
            return false;
        }
        
        String str = (String) valeur;
        
        if (longueurMin != null && str.length() < longueurMin) {
            return false;
        }
        
        if (longueurMax != null && str.length() > longueurMax) {
            return false;
        }
        
        if (regexValidation != null && !regexValidation.isEmpty() && !str.matches(regexValidation)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String getTypeAttente() {
        return "TEXT";
    }
}