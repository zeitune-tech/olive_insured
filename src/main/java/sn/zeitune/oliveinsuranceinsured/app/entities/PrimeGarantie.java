package sn.zeitune.oliveinsuranceinsured.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prime_garantie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrimeGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    // References (UUID)
    @Column(name = "pos_uuid", nullable = false)
    private UUID poSaleUuid;

    @Column(name = "police_uuid", nullable = false)
    private UUID policeUuid;

    @Column(name = "risk_uuid", nullable = false)
    private UUID riskUuid;

    @Column(name = "num_avenant")
    private String numAvenant;

    @Column(name = "quittance_uuid")
    private UUID quittanceUuid;

    @Column(name = "produit_uuid", nullable = false)
    private UUID produitUuid;

    @Column(name = "garantie_uuid", nullable = false)
    private UUID garantieUuid;

    // Financial fields
    @Column(name = "prime_nette", precision = 15, scale = 2)
    private BigDecimal primeNette;

    @Column(name = "taxe_prime", precision = 15, scale = 2)
    private BigDecimal taxePrime;

    @ElementCollection
    @CollectionTable(name = "prime_garantie_taxes", joinColumns = @JoinColumn(name = "prime_garantie_id"))
    @Column(name = "taxe_uuid")
    private List<UUID> taxeList = new ArrayList<>();

    @Column(name = "commission_apport", precision = 15, scale = 2)
    private BigDecimal commissionApport;

    @Column(name = "commission_gestion", precision = 15, scale = 2)
    private BigDecimal commissionGestion;

    @Column(name = "montant_reduction", precision = 15, scale = 2)
    private BigDecimal montantReduction;

    @Column(name = "montant_majoration", precision = 15, scale = 2)
    private BigDecimal montantMajoration;

    @Column(name = "sort_garanti", precision = 15, scale = 2)
    private BigDecimal sortGaranti;

    @Column(name = "compagnie_uuid", nullable = false)
    private UUID compagnieUuid;

    // Relation JPA avec le risque (même microservice)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private Risk risk;

    // Audit fields
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @PrePersist
    void prePersist() {
        if (uuid == null) uuid = UUID.randomUUID();
        createdAt = Instant.now();
        deleted = false;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}