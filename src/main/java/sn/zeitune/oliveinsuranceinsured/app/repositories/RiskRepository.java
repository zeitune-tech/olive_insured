package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsuranceinsured.app.entities.Risk;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RiskRepository extends JpaRepository<Risk, Long> {
    Optional<Risk> findByUuid(UUID uuid);
    Optional<Risk> findByImmatriculation(String immatriculation);
    Page<Risk> findAllByImmatriculationContainingIgnoreCase(String immatriculation, Pageable pageable);
    List<Risk> findByUuidIn(List<UUID> uuids);
    List<Risk> findByInsuredUuid(UUID insuredUuid);
}

