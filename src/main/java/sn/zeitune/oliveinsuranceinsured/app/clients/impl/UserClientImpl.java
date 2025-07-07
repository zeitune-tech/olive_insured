package sn.zeitune.oliveinsuranceinsured.app.clients.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.zeitune.oliveinsuranceinsured.app.clients.UserClient;

@Component
@Slf4j
public class UserClientImpl implements UserClient {

    private final WebClient userWebClient;

    public UserClientImpl(WebClient.Builder webClientBuilder) {
        this.userWebClient = webClientBuilder.baseUrl("http://localhost:8010/api/v1").build();
    }


    private Mono<Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
            log.error("❗ Error response from user-service: status={} body={}", response.statusCode(), errorBody);
            return Mono.error(new RuntimeException("User service call failed: " + errorBody));
        });
    }
}
