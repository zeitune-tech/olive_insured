package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NUMERIQUE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristiqueNumerique extends Caracteristique {

    @Column(name = "valeur_min", precision = 19, scale = 4)
    private BigDecimal valeurMin;

    @Column(name = "valeur_max", precision = 19, scale = 4)
    private BigDecimal valeurMax;

    @Column(name = "nombre_decimales")
    private Integer nombreDecimales = 0;

    @Override
    public boolean validerValeur(Object valeur) {
        if (valeur == null) {
            return !Boolean.TRUE.equals(getObligatoire());
        }
        
        BigDecimal num;
        try {
            if (valeur instanceof Number) {
                num = new BigDecimal(valeur.toString());
            } else if (valeur instanceof String) {
                num = new BigDecimal((String) valeur);
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        if (valeurMin != null && num.compareTo(valeurMin) < 0) {
            return false;
        }
        
        if (valeurMax != null && num.compareTo(valeurMax) > 0) {
            return false;
        }

        if (nombreDecimales != null && nombreDecimales >= 0) {
            int actualScale = num.scale();
            if (actualScale > nombreDecimales) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getTypeAttente() {
        return "NUMBER";
    }
}