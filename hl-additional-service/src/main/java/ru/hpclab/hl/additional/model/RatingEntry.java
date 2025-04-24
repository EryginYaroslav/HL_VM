// src/main/java/ru/hpclab/hl/additional/model/RatingEntry.java
package ru.hpclab.hl.additional.model;

public class RatingEntry {
    private Long clientId;
    private String fullName;
    private int caloriesBurned;

    public RatingEntry(Long clientId, String fullName, int caloriesBurned) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.caloriesBurned = caloriesBurned;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    public String toString() {
        return "RatingEntry{" +
               "clientId=" + clientId +
               ", fullName='" + fullName + '\'' +
               ", caloriesBurned=" + caloriesBurned +
               '}';
    }
}
