package ru.hpclab.hl.module1.service;

import ru.hpclab.hl.module1.model.Visit;
import ru.hpclab.hl.module1.repository.VisitRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Visit createVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public Optional<Visit> getVisit(Long id) {
        return visitRepository.findById(id);
    }

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }

    // ← И здесь тоже
    public void deleteAll() {
        visitRepository.deleteAll();
    }
}
