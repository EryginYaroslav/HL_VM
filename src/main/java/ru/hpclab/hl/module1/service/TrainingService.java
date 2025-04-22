package ru.hpclab.hl.module1.service;

import ru.hpclab.hl.module1.model.Training;
import ru.hpclab.hl.module1.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Optional<Training> getTraining(Long id) {
        return trainingRepository.findById(id);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    // ← Добавляем вот этот
    public void deleteAll() {
        trainingRepository.deleteAll();
    }
}
