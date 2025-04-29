package ru.hpclab.hl.additional.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

/**
 * DTO для JSON от /api/visit — 
 * соответствует структуре:
 * {
 *   "client": { "id": ..., "fullName": ... , … },
 *   "training": { … },
 *   "visitDate": "...",
 *   "caloriesBurned": ...
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitDto {
    private ClientDto client;
    private LocalDate visitDate;
    private int caloriesBurned;

    public VisitDto() {}

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}

