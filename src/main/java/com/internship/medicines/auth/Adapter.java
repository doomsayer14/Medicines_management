package com.internship.medicines.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Provides connection with other microservices
 */
@RequiredArgsConstructor
public class Adapter {

    private final RestTemplate restTemplate;

    public <T, V> ResponseEntity<T> getResponseEntity(MicroserviceURLS serviceResourceUrl, String authorization, Endpoint url, V body, Class<T> returnType, Object... params) {
        String path = String.format(url.getPath(), params);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        HttpEntity<V> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(serviceResourceUrl.getPath() + path, HttpMethod.GET, requestEntity, returnType);
    }

}