package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsuranceinsured.enums.Civilite;
import sn.zeitune.oliveinsuranceinsured.enums.TypePiece;

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
@Table(name = "insured",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_insured_piece", columnNames = {"id_document_type", "id_document_number"}),
                @UniqueConstraint(name = "uk_insured_uuid", columnNames = {"uuid"})
        },
        indexes = {
                @Index(name = "idx_insured_nom_prenom", columnList = "last_name, first_name"),
                @Index(name = "idx_insured_email", columnList = "email")
        }
)

public class Insured {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "civility")
    private Civilite civility;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_document_type")
    private TypePiece idDocumentType;

    @Column(name = "id_document_number")
    private String idDocumentNumber;

    private String address;

    private String city;
    private String profession;
    private String activity;

    private String email;
    @Column(name = "landline_phone")
    private String landlinePhone;
    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "business_license")
    private String businessLicense;
    @Column(name = "trade_register")
    private String tradeRegister;

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

