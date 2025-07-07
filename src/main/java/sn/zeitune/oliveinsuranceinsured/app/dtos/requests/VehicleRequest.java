package sn.zeitune.oliveinsuranceinsured.app.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsuranceinsured.enums.FuelType;
import sn.zeitune.oliveinsuranceinsured.enums.GearboxType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleUsage;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record VehicleRequest(

        @NotBlank(message = "License plate must not be blank")
        String licensePlate,

        @NotBlank(message = "Brand must not be blank")
        String brand,

        @NotBlank(message = "Model must not be blank")
        String model,

        @NotNull(message = "Fuel type must not be null")
        FuelType fuelType,

        @NotNull(message = "Gearbox type must not be null")
        GearboxType gearboxType,

        @NotNull(message = "Fiscal power must not be null")
        @Positive(message = "Fiscal power must be positive")
        Integer fiscalPower,

        @NotNull(message = "Real power must not be null")
        @Positive(message = "Real power must be positive")
        Integer realPower,

        @NotNull(message = "Seating capacity must not be null")
        @Positive(message = "Seating capacity must be positive")
        Integer seatingCapacity,

        @NotNull(message = "First registration date must not be null")
        @PastOrPresent(message = "First registration date cannot be in the future")
        LocalDate firstRegistrationDate,

        @NotNull(message = "Initial value must not be null")
        @Positive(message = "Initial value must be positive")
        Double initialValue,

        @NotNull(message = "Current value must not be null")
        @Positive(message = "Current value must be positive")
        Double currentValue,

        @NotBlank(message = "VIN must not be blank")
        String vin,

        @NotNull(message = "Vehicle usage must not be null")
        VehicleUsage vehicleUsage,

        @NotNull(message = "Vehicle type must not be null")
        VehicleType vehicleType,

        @NotNull(message = "Mileage must not be null")
        @PositiveOrZero(message = "Mileage must be zero or positive")
        Integer mileage,

        @NotBlank(message = "Usual parking location must not be blank")
        String usualParkingLocation,

        @NotNull(message = "Insured UUID must not be null")
        UUID insuredId
) {}
