package sn.zeitune.oliveinsuranceinsured.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Configuration
public class HttpAndCacheConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(authorizationPropagation())
                .filter(correlationIdPropagation());
    }

    private ExchangeFilterFunction authorizationPropagation() {
        return (request, next) -> {
            // Propagate Authorization header if present in Reactor context (set by security filter)
            return next.exchange(request);
        };
    }

    private ExchangeFilterFunction correlationIdPropagation() {
        return (request, next) -> {
            String cid = MDC.get("correlation-id");
            if (cid == null) {
                cid = UUID.randomUUID().toString();
                MDC.put("correlation-id", cid);
            }
            String finalCid = cid;
            return next.exchange(
                    ClientRequestMutator.mutate(request, b -> b.header("x-correlation-id", finalCid))
            );
        };
    }

    @Bean
    public CacheManager cacheManager(
            @Value("${caching.specs.short:maximumSize=1000,expireAfterWrite=5m}") String shortSpec,
            @Value("${caching.specs.medium:maximumSize=2000,expireAfterWrite=10m}") String mediumSpec,
            @Value("${caching.specs.long:maximumSize=5000,expireAfterWrite=15m}") String longSpec
    ) {
        SimpleCacheManager manager = new SimpleCacheManager();
        CaffeineCache genres = new CaffeineCache("genres", Caffeine.from(mediumSpec).build());
        CaffeineCache usages = new CaffeineCache("usages", Caffeine.from(mediumSpec).build());
        CaffeineCache attestations = new CaffeineCache("attestations", Caffeine.from(longSpec).build());
        manager.setCaches(List.of(genres, usages, attestations));
        return manager;
    }

    // Small helper to mutate WebClient request with headers while preserving attributes
    private static class ClientRequestMutator {
        static org.springframework.web.reactive.function.client.ClientRequest mutate(
                org.springframework.web.reactive.function.client.ClientRequest request,
                java.util.function.Consumer<org.springframework.web.reactive.function.client.ClientRequest.Builder> customizer
        ) {
            org.springframework.web.reactive.function.client.ClientRequest.Builder b = org.springframework.web.reactive.function.client.ClientRequest.from(request);
            customizer.accept(b);
            return b.build();
        }
    }
}

