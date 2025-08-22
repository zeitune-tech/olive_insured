package sn.zeitune.oliveinsuranceinsured.app.clients.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sn.zeitune.oliveinsuranceinsured.app.clients.AttestationClient;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttestationClientImpl implements AttestationClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.olive-insurance-attestation-service.base-url:http://localhost:8050}")
    private String baseUrl;

    private WebClient client() {
        return webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    @Cacheable(cacheNames = "attestations", key = "#uuid")
    public Optional<AttestationDto> get(UUID uuid) {
        try {
            var dto = client().get()
                    .uri("/interservices/attestations/{uuid}", uuid)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToMono(AttestationDto.class)
                    .block();
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.warn("Attestation fetch failed for {}: {}", uuid, e.getMessage());
            return Optional.empty();
        }
    }
}

