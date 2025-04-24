package ru.hpclab.hl.module1.controller;

import ru.hpclab.hl.module1.service.ClientService;
import ru.hpclab.hl.module1.service.TrainingService;
import ru.hpclab.hl.module1.service.VisitService;
import ru.hpclab.hl.module1.service.RatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ClientService clientService;
    private final TrainingService trainingService;
    private final VisitService visitService;
    private final RatingService ratingService;

    public AdminController(ClientService clientService,
                           TrainingService trainingService,
                           VisitService visitService,
                           RatingService ratingService) {
        this.clientService = clientService;
        this.trainingService = trainingService;
        this.visitService = visitService;
        this.ratingService = ratingService;
    }

    /** Полная очистка всех таблиц */
    @DeleteMapping("/clear")
    public void clearAll() {
        visitService.deleteAll();
        trainingService.deleteAll();
        clientService.deleteAll();
    }

    /** Рейтинг за последний месяц */
    @GetMapping("/rating")
    public List<RatingService.RatingEntry> rating() {
        return ratingService.getMonthlyActivityRating();
    }
}
