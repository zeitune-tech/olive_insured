package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsuranceinsured.enums.Energie;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.TypeCarrosserie;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "risk",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_risk_immatriculation", columnNames = {"immatriculation"}),
                @UniqueConstraint(name = "uk_risk_uuid", columnNames = {"uuid"})
        },
        indexes = {
                @Index(name = "idx_risk_genre", columnList = "genre_uuid"),
                @Index(name = "idx_risk_usage", columnList = "usage_uuid"),
                @Index(name = "idx_risk_attestation", columnList = "num_attestation_uuid")
        }
)
//@SQLDelete(sql = "UPDATE risk SET deleted = true, deleted_at = now() WHERE id = ?")
//@Where(clause = "deleted = false")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Column(name = "is_fleet_member")
    private Boolean isFleetMember = false;

    @Column(name = "parent_fleet_police_uuid")
    private UUID parentFleetPoliceUuid;

    private Integer numAvenant;

    @Column(nullable = false)
    private String immatriculation;

    private Integer ordre;

    // #ref copied labels
    private String marque;
    private String modele;

    // ##ref strong references
    @Column(name = "produit_uuid")
    private UUID produitUuid;

    @Column(name = "insured_uuid")
    private UUID insuredUuid;

    @OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RiskDriverEntry> driverEntries;

    @Column(name = "nom_conducteur")
    private String nomConducteur;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe_conducteur")
    private Gender sexeConducteur;

    @Column(name = "date_naissance_conducteur")
    private LocalDate dateNaissanceConducteur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_permis")
    private LicenseCategory typePermis;

    @Column(name = "num_permis")
    private String numPermis;

    @Column(name = "date_delivrance_permis")
    private LocalDate dateDelivrancePermis;

    @Column(name = "lieu_delivrance_permis")
    private String lieuDelivrancePermis;

    @Column(name = "delegation_credit")
    private Boolean delegationCredit = false;

    private String zone;

    @Column(name = "genre_uuid")
    private UUID genreUuid;
    @Column(name = "usage_uuid")
    private UUID usageUuid;

    @Column(name = "date_mise_en_circulation")
    private LocalDate dateMiseEnCirculation;

    @Enumerated(EnumType.STRING)
    private Energie energie;

    @Column(name = "num_chassis")
    private String numChassis;
    @Column(name = "num_moteur")
    private String numMoteur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_carrosserie")
    private TypeCarrosserie typeCarrosserie;

    private Boolean hasTurbo;
    private Boolean hasRemorque;
    private Boolean isEnflammable;

    private BigDecimal puissance;
    private BigDecimal tonnage;
    private BigDecimal cylindre;
    private Integer nbPlace;

    @Column(name = "num_attestation_uuid")
    private UUID numAttestationUuid;

    @Column(name = "formule_pt_uuid")
    private UUID formulePTUuid;

    // Business fields
    private Integer nbPT;

    @Column(name = "vitesse")
    private Integer vitesse;

    @Column(name = "valeur_a_neuve")
    private BigDecimal valeurANeuve;
    @Column(name = "valeur_venale")
    private BigDecimal valeurVenale;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;
    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    public void prePersist() {
        if (uuid == null) uuid = UUID.randomUUID();
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}

