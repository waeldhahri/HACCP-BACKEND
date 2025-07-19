package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planningNettoyages")
@CrossOrigin
public class PlanningNettoyagePosteController {



    private final PlanningNettoyagePosteService service;

    public PlanningNettoyagePosteController(PlanningNettoyagePosteService service) {
        this.service = service;
    }

    @PostMapping("/addPlanningNettoyagePoste")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningNettoyagePoste> create(@RequestBody PlanningNettoyagePosteDTO dto) {
        return ResponseEntity.ok(service.createPlanning(dto));
    }

    @GetMapping("/getAllPlanningNettoyagePoste")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningNettoyagePoste>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }





    // GET : récupérer un planningHuile par ID
    @GetMapping("/getPlanningNettoyagePoste/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public PlanningNettoyagePoste getPlanningNettoyageById(@PathVariable Long id) {
        return service.getPlanningNettoyagePosteById(id);
    }

    @PutMapping("/PlanningNettoyagePoste/{id}/check")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningNettoyagePoste> checkPlanning(@PathVariable Long id) {
        PlanningNettoyagePoste updated = service.checkPlanning(id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/today")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningNettoyagePoste>> getTodayPlannings() {
        return ResponseEntity.ok(service.getPlanningsForToday());
    }

    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningNettoyagePoste>> getPlanningsBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return ResponseEntity.ok(service.getPlanningsBetweenDates(start, end));
    }



    @GetMapping("/date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningNettoyagePoste>> getPlanningsByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(service.getPlanningHuileForAday(date));
    }
}
