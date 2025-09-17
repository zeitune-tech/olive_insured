package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_caracteristique")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionCaracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "valeur", nullable = false)
    private String valeur;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "ordre")
    private Integer ordre;

    @ManyToOne
    @JoinColumn(name = "caracteristique_id", nullable = false)
    @JsonIgnore
    private CaracteristiqueListe caracteristique;

    @Column(name = "actif")
    private Boolean actif = true;
}