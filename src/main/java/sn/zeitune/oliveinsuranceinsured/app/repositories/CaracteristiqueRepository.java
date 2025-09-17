package sn.zeitune.oliveinsuranceinsured.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsuranceinsured.app.entities.attributes.Caracteristique;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CaracteristiqueRepository extends JpaRepository<Caracteristique, Long> {
    Optional<Caracteristique> findByUuid(UUID uuid);
    List<Caracteristique> findByActifTrue();
    List<Caracteristique> findByActifTrueOrderByOrdreAffichageAsc();
    List<Caracteristique> findByNomContainingIgnoreCaseAndActifTrue(String nom);
}