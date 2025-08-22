package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsuranceinsured.enums.Civilite;
import sn.zeitune.oliveinsuranceinsured.enums.TypePiece;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "insured",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_insured_piece", columnNames = {"type_piece", "numero_piece"}),
                @UniqueConstraint(name = "uk_insured_uuid", columnNames = {"uuid"})
        },
        indexes = {
                @Index(name = "idx_insured_nom_prenom", columnList = "nom, prenom"),
                @Index(name = "idx_insured_email", columnList = "email")
        }
)
//@SQLDelete(sql = "UPDATE insured SET deleted = true, deleted_at = now() WHERE id = ?")
//@Where(clause = "deleted = false")
public class Insured {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_piece")
    private TypePiece typePiece;

    @Column(name = "numero_piece")
    private String numeroPiece;

    private String adresse;

    // denormalized labels (#ref)
    private String ville;
    private String profession;
    private String activite;
    private String qualite;

    private String email;
    @Column(name = "phone_fixe")
    private String phoneFixe;
    @Column(name = "phone_mobile")
    private String phoneMobile;

    @Column(name = "nom_entreprise")
    private String nomEntreprise;
    private String patente;
    @Column(name = "registre_de_commerce")
    private String registreDeCommerce;

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

