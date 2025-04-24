// src/main/java/ru/hpclab/hl/additional/controller/AdditionalController.java
package ru.hpclab.hl.additional.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hpclab.hl.additional.model.RatingEntry;
import ru.hpclab.hl.additional.service.AdditionalRatingService;

import java.util.List;

@RestController
@RequestMapping("/api/additional")
public class AdditionalController {

    private final AdditionalRatingService ratingService;

    public AdditionalController(AdditionalRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/rating")
    public List<RatingEntry> getRating() {
        return ratingService.computeRating();
    }
}
