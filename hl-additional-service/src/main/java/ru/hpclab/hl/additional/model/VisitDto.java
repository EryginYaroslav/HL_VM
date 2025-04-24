// src/main/java/ru/hpclab/hl/additional/model/VisitDto.java
package ru.hpclab.hl.additional.model;

import java.time.LocalDate;

public class VisitDto {
    private Long clientId;
    private LocalDate visitDate;
    private int caloriesBurned;
    // конструктор по умолчанию, геттеры/сеттеры

    public VisitDto() {}

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
