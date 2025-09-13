package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsuranceinsured.app.entities.PrimeGarantie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrimeGarantieRepository extends JpaRepository<PrimeGarantie, Long> {

    Optional<PrimeGarantie> findByUuidAndDeletedFalse(UUID uuid);

    List<PrimeGarantie> findByRiskUuidAndDeletedFalse(UUID riskUuid);

    List<PrimeGarantie> findByPoliceUuidAndDeletedFalse(UUID policeUuid);

    List<PrimeGarantie> findByGarantieUuidAndDeletedFalse(UUID garantieUuid);

    Page<PrimeGarantie> findByDeletedFalse(Pageable pageable);

    @Query("SELECT pg FROM PrimeGarantie pg WHERE pg.deleted = false " +
           "AND (:policeUuid IS NULL OR pg.policeUuid = :policeUuid) " +
           "AND (:riskUuid IS NULL OR pg.riskUuid = :riskUuid) " +
           "AND (:garantieUuid IS NULL OR pg.garantieUuid = :garantieUuid)")
    Page<PrimeGarantie> findWithFilters(@Param("policeUuid") UUID policeUuid,
                                        @Param("riskUuid") UUID riskUuid,
                                        @Param("garantieUuid") UUID garantieUuid,
                                        Pageable pageable);

    @Query("SELECT COUNT(pg) FROM PrimeGarantie pg WHERE pg.riskUuid = :riskUuid AND pg.deleted = false")
    long countByRiskUuid(@Param("riskUuid") UUID riskUuid);

    boolean existsByUuidAndDeletedFalse(UUID uuid);
}