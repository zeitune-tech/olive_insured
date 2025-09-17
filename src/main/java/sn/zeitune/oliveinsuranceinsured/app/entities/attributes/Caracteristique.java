package sn.zeitune.oliveinsuranceinsured.app.entities.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "caracteristique")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_caracteristique", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Caracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "obligatoire")
    private Boolean obligatoire = false;

    @Column(name = "ordre_affichage")
    private Integer ordreAffichage;

    @Column(name = "actif")
    private Boolean actif = true;

    // Portée et applicabilité
    @Column(name = "company_uuid")
    private UUID companyUuid; // si null: applicable à toutes

    @ElementCollection
    @CollectionTable(name = "caracteristique_warranties", joinColumns = @JoinColumn(name = "caracteristique_id"))
    @Column(name = "warranty_uuid")
    private List<UUID> warrantiesUuids = new ArrayList<>();

    public abstract boolean validerValeur(Object valeur);

    public abstract String getTypeAttente();
}
