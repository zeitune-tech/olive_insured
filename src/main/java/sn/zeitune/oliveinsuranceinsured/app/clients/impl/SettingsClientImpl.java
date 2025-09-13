package sn.zeitune.oliveinsuranceinsured.app.clients.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sn.zeitune.oliveinsuranceinsured.app.clients.SettingsClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SettingsClientImpl implements SettingsClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.olive-insurance-settings-service.base-url:http://localhost:8060}")
    private String baseUrl;

    private WebClient client() {
        return webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Optional<VilleDto> searchVilleByName(String q) {
        try {
            var dto = client().get()
                    .uri(uri -> uri.path("/interservices/settings/villes").queryParam("query", q).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToMono(VilleDto.class)
                    .block();
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.warn("Settings ville resolution failed for {}: {}", q, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Cacheable(cacheNames = "genres", key = "#uuid")
    public Optional<GenreDto> getGenre(UUID uuid) {
        try {
            var dto = client().get()
                    .uri("/interservices/settings/genres/{uuid}", uuid)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToMono(GenreDto.class)
                    .block();
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.warn("Settings genre fetch failed for {}: {}", uuid, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Cacheable(cacheNames = "usages", key = "#uuid")
    public Optional<UsageDto> getUsage(UUID uuid) {
        try {
            var dto = client().get()
                    .uri("/interservices/settings/usages/{uuid}", uuid)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToMono(UsageDto.class)
                    .block();
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.warn("Settings usage fetch failed for {}: {}", uuid, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<BrandDto> searchBrands(String q) {
        try {
            var list = client().get()
                    .uri(uri -> uri.path("/interservices/settings/brands").queryParam("query", q).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToFlux(BrandDto.class)
                    .collectList()
                    .block();
            return list == null ? Collections.emptyList() : list;
        } catch (Exception e) {
            log.warn("Settings brands search failed for {}: {}", q, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(cacheNames = "vehicleTypes", key = "#uuid")
    public Optional<VehicleTypeDto> getVehicleType(UUID uuid) {
        try {
            var dto = client().get()
                    .uri("/interservices/settings/vehicle-types/{uuid}", uuid)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToMono(VehicleTypeDto.class)
                    .block();
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.warn("Settings vehicle type fetch failed for {}: {}", uuid, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<ModelDto> searchModels(String brand, String q) {
        try {
            var list = client().get()
                    .uri(uri -> uri.path("/interservices/settings/models").queryParam("brand", brand).queryParam("query", q).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class).map(RuntimeException::new))
                    .bodyToFlux(ModelDto.class)
                    .collectList()
                    .block();
            return list == null ? Collections.emptyList() : list;
        } catch (Exception e) {
            log.warn("Settings models search failed for brand {} q {}: {}", brand, q, e.getMessage());
            return Collections.emptyList();
        }
    }
}

