package sn.zeitune.oliveinsuranceinsured.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsuranceinsured.enums.FuelType;
import sn.zeitune.oliveinsuranceinsured.enums.GearboxType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleType;
import sn.zeitune.oliveinsuranceinsured.enums.VehicleUsage;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record VehicleResponse(
        UUID id,
        String licensePlate,
        String brand,
        String model,
        FuelType fuelType,
        GearboxType gearboxType,
        Integer fiscalPower,
        Integer realPower,
        Integer seatingCapacity,
        LocalDate firstRegistrationDate,
        Double initialValue,
        Double currentValue,
        String vin,
        VehicleUsage vehicleUsage,
        VehicleType vehicleType,
        Integer mileage,
        String usualParkingLocation,
        InsuredResponse insured
) {}
