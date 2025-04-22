package ru.hpclab.hl.module1.controller;

import ru.hpclab.hl.module1.model.Training;
import ru.hpclab.hl.module1.service.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public Training create(@RequestBody Training training) {
        return trainingService.createTraining(training);
    }

    @GetMapping
    public List<Training> getAll() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public Training getOne(@PathVariable Long id) {
        return trainingService.getTraining(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        trainingService.deleteTraining(id);
    }
}
