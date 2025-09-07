package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsuranceinsured.app.entities.RiskDriverEntry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RiskDriverEntryRepository extends JpaRepository<RiskDriverEntry, Long> {
    Optional<RiskDriverEntry> findByUuid(UUID uuid);
    List<RiskDriverEntry> findAllByRiskUuid(UUID riskUuid);
    List<RiskDriverEntry> findAllByRiskUuidOrderByDateEntreeDesc(UUID riskUuid);
}