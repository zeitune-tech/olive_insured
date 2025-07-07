package sn.zeitune.oliveinsuranceinsured.app.mappers;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.VehicleRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.VehicleResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Vehicle;

public class VehicleMapper {

    public static Vehicle map(VehicleRequest request) {
        return Vehicle.builder()
                .licensePlate(request.licensePlate())
                .brand(request.brand())
                .model(request.model())
                .fuelType(request.fuelType())
                .gearboxType(request.gearboxType())
                .fiscalPower(request.fiscalPower())
                .realPower(request.realPower())
                .seatingCapacity(request.seatingCapacity())
                .firstRegistrationDate(request.firstRegistrationDate())
                .initialValue(request.initialValue())
                .currentValue(request.currentValue())
                .vin(request.vin())
                .vehicleUsage(request.vehicleUsage())
                .vehicleType(request.vehicleType())
                .mileage(request.mileage())
                .usualParkingLocation(request.usualParkingLocation())
                .build();
    }

    public static VehicleResponse map(Vehicle vehicle, InsuredResponse insured) {
        return VehicleResponse.builder()
                .id(vehicle.getUuid())
                .licensePlate(vehicle.getLicensePlate())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .fuelType(vehicle.getFuelType())
                .gearboxType(vehicle.getGearboxType())
                .fiscalPower(vehicle.getFiscalPower())
                .realPower(vehicle.getRealPower())
                .seatingCapacity(vehicle.getSeatingCapacity())
                .firstRegistrationDate(vehicle.getFirstRegistrationDate())
                .initialValue(vehicle.getInitialValue())
                .currentValue(vehicle.getCurrentValue())
                .vin(vehicle.getVin())
                .vehicleUsage(vehicle.getVehicleUsage())
                .vehicleType(vehicle.getVehicleType())
                .mileage(vehicle.getMileage())
                .usualParkingLocation(vehicle.getUsualParkingLocation())
                .insured(insured)
                .build();
    }
}
