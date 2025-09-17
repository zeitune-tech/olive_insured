package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;

import java.util.UUID;

@Entity
@Table(name = "valeur_caracteristique_risk",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_risk_caracteristique_entite",
           columnNames = {"risk_id", "caracteristique_id", "entite_associee_uuid"}
       ))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValeurCaracteristiqueRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caracteristique_id", nullable = false)
    private Caracteristique caracteristique;

    @Column(name = "valeur", nullable = false, columnDefinition = "TEXT")
    private String valeur;

    @Column(name = "entite_associee_uuid")
    private UUID entiteAssocieeUuid;

}