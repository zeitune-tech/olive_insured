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
import java.util.ArrayList;
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
                @Index(name = "idx_risk_usage", columnList = "usage_uuid")
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
    @Column(name = "product_uuid")
    private UUID productUuid;

    // Relation JPA avec l'assuré (même microservice)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insured_uuid", referencedColumnName = "uuid")
    private Insured insured;

    @OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RiskDriverEntry> driverEntries;

    @Column(name = "driver_name")
    private String driverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_gender")
    private Gender driverGender;

    @Column(name = "driver_birth_date")
    private LocalDate driverBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_type")
    private LicenseCategory licenseType;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "license_issue_date")
    private LocalDate licenseIssueDate;

    @Column(name = "license_issue_place")
    private String licenseIssuePlace;

    @Column(name = "credit_delegation")
    private Boolean creditDelegation = false;

    private String zone;

    @Column(name = "vehicle_type_uuid")
    private UUID vehicleTypeUuid;

    @Column(name = "genre_uuid")
    private UUID genreUuid;

    @Column(name = "usage_uuid")
    private UUID usageUuid;

    @Column(name = "first_registration_date")
    private LocalDate firstRegistrationDate;

    @Enumerated(EnumType.STRING)
    private Energie energie;

    @Column(name = "chassis_number")
    private String chassisNumber;
    @Column(name = "engine_number")
    private String engineNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private TypeCarrosserie bodyType;

    private Boolean hasTurbo;
    private Boolean hasTrailer;
    private Boolean isFlammable;

    private BigDecimal power;
    private BigDecimal tonnage;
    private BigDecimal  cylinder;
    private Integer seatCount;

    @Column(name = "attestation_number_uuid")
    private UUID attestationNumberUuid;

    @Column(name = "formula_pt_uuid")
    private UUID formulaPTUuid;

    // Business fields - moved from Police for fleet support
    @Column(name = "nb_persons_transported")
    private Integer nbPersonsTransported;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    @Column(name = "new_value")
    private BigDecimal newValue;
    @Column(name = "market_value")
    private BigDecimal marketValue;

    // Primes garanties (déplacées de Police vers Risk car propres à chaque risque)
    @OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PrimeGarantie> primesGaranties = new ArrayList<>();

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

