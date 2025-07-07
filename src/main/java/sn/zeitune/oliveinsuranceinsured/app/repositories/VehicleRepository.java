package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsuranceinsured.app.entities.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    Optional<Vehicle> findByUuid(UUID uuid);

    List<Vehicle> findAllByInsuredUuid(UUID insuredUuid);

    List<Vehicle> findAllByManagementEntity(UUID managementEntity);
}
