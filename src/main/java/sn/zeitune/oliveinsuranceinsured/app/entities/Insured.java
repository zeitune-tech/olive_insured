package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsuranceinsured.enums.EmploymentStatus;
import sn.zeitune.oliveinsuranceinsured.enums.Gender;
import sn.zeitune.oliveinsuranceinsured.enums.LicenseCategory;
import sn.zeitune.oliveinsuranceinsured.enums.MaritalStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity(name = "assure")
@NoArgsConstructor
@AllArgsConstructor
public class Insured {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    // Personal Information
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    // Administrative Information
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "license_issue_date", nullable = false)
    private LocalDate licenseIssueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_category", nullable = false)
    private LicenseCategory licenseCategory;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "profession", nullable = false)
    private String profession;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false)
    private EmploymentStatus employmentStatus;

    private UUID managementEntity;
}