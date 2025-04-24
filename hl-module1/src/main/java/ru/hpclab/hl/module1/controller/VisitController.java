package ru.hpclab.hl.module1.controller;

import ru.hpclab.hl.module1.model.Visit;
import ru.hpclab.hl.module1.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public Visit create(@RequestBody Visit visit) {
        return visitService.createVisit(visit);
    }

    @GetMapping
    public List<Visit> getAll() {
        return visitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public Visit getOne(@PathVariable Long id) {
        return visitService.getVisit(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        visitService.deleteVisit(id);
    }
}
