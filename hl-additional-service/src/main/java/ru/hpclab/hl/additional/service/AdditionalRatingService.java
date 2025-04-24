// src/main/java/ru/hpclab/hl/additional/service/AdditionalRatingService.java
package ru.hpclab.hl.additional.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.additional.model.ClientDto;
import ru.hpclab.hl.additional.model.VisitDto;
import ru.hpclab.hl.additional.model.RatingEntry;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdditionalRatingService {

    @Value("${crud.service.url}")
    private String crudBaseUrl;

    private final RestTemplate restTemplate;

    public AdditionalRatingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RatingEntry> computeRating() {
        ClientDto[] clients = restTemplate.getForObject(
            crudBaseUrl + "/api/client", ClientDto[].class);
        VisitDto[] visits = restTemplate.getForObject(
            crudBaseUrl + "/api/visit", VisitDto[].class);

        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Map<Long, Integer> sumByClient = Arrays.stream(visits)
            .filter(v -> v.getVisitDate().isAfter(oneMonthAgo))
            .collect(Collectors.groupingBy(
                VisitDto::getClientId,
                Collectors.summingInt(VisitDto::getCaloriesBurned)
            ));

        return Arrays.stream(clients)
            .map(c -> new RatingEntry(
                c.getId(),
                c.getFullName(),
                sumByClient.getOrDefault(c.getId(), 0)
            ))
            .sorted((a, b) -> Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned()))
            .toList();
    }
}
