package sn.zeitune.oliveinsuranceinsured.app.clients;

import java.util.Optional;
import java.util.UUID;

public interface AttestationClient {
    Optional<AttestationDto> get(UUID uuid);

    record AttestationDto(UUID uuid, String numero, String statut) {}
}

