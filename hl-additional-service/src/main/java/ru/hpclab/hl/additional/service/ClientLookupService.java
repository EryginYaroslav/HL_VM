// src/main/java/ru.hpclab/hl/additional/service/ClientLookupService.java
package ru.hpclab.hl.additional.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.additional.model.ClientDto;

@Service
public class ClientLookupService {

    @Value("${crud.service.url}")
    private String crudBaseUrl;

    private final RestTemplate restTemplate;

    public ClientLookupService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClientDto getClientById(Long clientId) {
        try {
            return restTemplate.getForObject(
                crudBaseUrl + "/api/client/" + clientId,
                ClientDto.class
            );
        } catch (RestClientException ex) {
            return null;
        }
    }
}

