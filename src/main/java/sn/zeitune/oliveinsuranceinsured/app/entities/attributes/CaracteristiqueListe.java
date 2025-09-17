package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("LISTE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristiqueListe extends Caracteristique {

    @Column(name = "selection_multiple")
    private Boolean selectionMultiple = false;

    @OneToMany(mappedBy = "caracteristique", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("ordre ASC")
    private List<OptionCaracteristique> options = new ArrayList<>();

    @Override
    public boolean validerValeur(Object valeur) {
        if (valeur == null) {
            return !Boolean.TRUE.equals(getObligatoire());
        }
        
        if (valeur instanceof String) {
            String strValue = (String) valeur;
            return options.stream()
                    .filter(opt -> Boolean.TRUE.equals(opt.getActif()))
                    .anyMatch(opt -> opt.getValeur().equals(strValue));
        }
        
        if (Boolean.TRUE.equals(selectionMultiple) && valeur instanceof List) {
            List<?> values = (List<?>) valeur;
            if (values.isEmpty()) {
                return !Boolean.TRUE.equals(getObligatoire());
            }
            
            return values.stream().allMatch(v -> 
                options.stream()
                        .filter(opt -> Boolean.TRUE.equals(opt.getActif()))
                        .anyMatch(opt -> opt.getValeur().equals(v.toString()))
            );
        }
        
        return false;
    }

    @Override
    public String getTypeAttente() {
        return Boolean.TRUE.equals(selectionMultiple) ? "MULTI_SELECT" : "SELECT";
    }
}