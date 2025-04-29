// src/main/java/ru/hpclab/hl/additional/service/AdditionalRatingService.java
package ru.hpclab.hl.additional.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.additional.model.VisitDto;
import ru.hpclab.hl.additional.model.RatingEntry;
import ru.hpclab.hl.additional.model.ClientDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdditionalRatingService {

    private final StatisticsCacheService cacheService;
    private final ClientLookupService lookupService;
    private final RestTemplate restTemplate;
    private final String visitUrl;

    public AdditionalRatingService(
            StatisticsCacheService cacheService,
            ClientLookupService lookupService,
            RestTemplate restTemplate,
            @Value("${crud.service.url}") String crudBaseUrl) {
        this.cacheService = cacheService;
        this.lookupService = lookupService;
        this.restTemplate = restTemplate;
        this.visitUrl = crudBaseUrl + "/api/visit";
    }

    public List<RatingEntry> computeRating() {
        //  Получаем все визиты за последний месяц
        VisitDto[] visits = restTemplate.getForObject(visitUrl, VisitDto[].class);
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        // Суммируем калории по clientId
        Map<Long, Integer> caloriesByClient = Arrays.stream(visits)
                .filter(v -> v.getVisitDate().isAfter(oneMonthAgo))
                .collect(Collectors.groupingBy(
                        v -> v.getClient().getId(),
                        Collectors.summingInt(VisitDto::getCaloriesBurned)
                ));

        // Для каждого clientId: сначала из кеша, иначе — синхронно из сервиса, и сразу в кеш
        return caloriesByClient.entrySet().stream()
                .map(entry -> {
                    Long clientId = entry.getKey();
                    int totalCalories = entry.getValue();

                    // попытаться взять из кеша
                    ClientDto client = cacheService.getClient(clientId);
                    if (client == null) {
                        // если нет — получить из ClientLookupService
                        client = lookupService.getClientById(clientId);
                        // и сразу сохранить в кеш (если успешно)
                        cacheService.putClient(clientId, client);
                    }

                    String fullName = (client != null)
                            ? client.getFullName()
                            : "Unknown Client";

                    return new RatingEntry(clientId, fullName, totalCalories);
                })
                // Сортируем по убыванию калорий
                .sorted((a, b) -> Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned()))
                .toList();
    }
}

