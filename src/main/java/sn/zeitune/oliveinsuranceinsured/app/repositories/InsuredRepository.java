package sn.zeitune.oliveinsuranceinsured.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InsuredRepository extends JpaRepository<Insured, Long>, JpaSpecificationExecutor<Insured> {

    Optional<Insured> findByUuid(UUID uuid);

    List<Insured> findAllByManagementEntity(UUID managementEntity);
}
