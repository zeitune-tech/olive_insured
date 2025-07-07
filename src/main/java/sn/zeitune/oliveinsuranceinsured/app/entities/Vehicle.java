package sn.zeitune.oliveinsuranceinsured.app.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsuranceinsured.enums.FuelType;
import sn.zeitune.oliveinsuranceinsured.enums.GearboxType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleUsage;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity(name = "vehicule")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

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

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gearbox_type")
    private GearboxType gearboxType;

    @Column(name = "fiscal_power")
    private Integer fiscalPower;

    @Column(name = "real_power")
    private Integer realPower;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "first_registration_date")
    private LocalDate firstRegistrationDate;

    @Column(name = "initial_value")
    private Double initialValue;

    @Column(name = "current_value")
    private Double currentValue;

    @Column(name = "vin", unique = true)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_usage")
    private VehicleUsage vehicleUsage;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "usual_parking_location")
    private String usualParkingLocation;

    @ManyToOne
    @JoinColumn(name = "code_assure", referencedColumnName = "id")
    private Insured insured;

    private UUID managementEntity;
}
