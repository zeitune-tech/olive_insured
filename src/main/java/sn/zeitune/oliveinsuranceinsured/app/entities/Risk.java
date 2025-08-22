package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsuranceinsured.enums.Energie;
import sn.zeitune.oliveinsuranceinsured.enums.TypeCarrosserie;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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

    @Column(nullable = false)
    private String immatriculation;

    // #ref copied labels
    private String marque;
    private String modele;

    // ##ref strong references
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

