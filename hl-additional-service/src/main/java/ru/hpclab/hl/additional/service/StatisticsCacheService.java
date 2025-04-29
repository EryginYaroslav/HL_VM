// src/main/java/ru/hpclab/hl/additional/service/StatisticsCacheService.java
package ru.hpclab.hl.additional.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.additional.model.ClientDto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class StatisticsCacheService {

    private final ConcurrentMap<Long, ClientDto> clientCache = new ConcurrentHashMap<>();

    @Value("${statistics.fixedRate.millis:60000}")
    private long fixedRate;

    //Получаем кеш
    public ClientDto getClient(Long clientId) {
        return clientCache.get(clientId);
    }
    //Кладем в кеш
    public void putClient(Long clientId, ClientDto client) {
        if (client != null) {
            clientCache.put(clientId, client);
        }
    }
}

