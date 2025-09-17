package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.ValeurCaracteristiqueRisk;

import java.util.List;
import java.util.UUID;

public interface ValeurCaracteristiquePoliceRepository extends JpaRepository<ValeurCaracteristiqueRisk, Long> {
    List<ValeurCaracteristiqueRisk> findAllByRisk_Uuid(UUID policeUuid);
}

