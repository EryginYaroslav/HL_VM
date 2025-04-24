// src/main/java/ru/hpclab/hl/additional/model/ClientDto.java
package ru.hpclab.hl.additional.model;

public class ClientDto {
    private Long id;
    private String fullName;
    // конструктор по умолчанию, геттеры/сеттеры

    public ClientDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
