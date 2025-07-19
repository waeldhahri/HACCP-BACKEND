package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile;


import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste.PlanningNettoyagePoste;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planning-huiles")
@CrossOrigin
public class PlanningSuiviHuileController {


    private final PlanningHuileService planningHuileService;

    public PlanningSuiviHuileController(PlanningHuileService planningHuileService) {
        this.planningHuileService = planningHuileService;
    }

    @PostMapping("/addPlanningHuile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningHuile> createPlanningHuile(@RequestBody PlanningHuileDTO dto) {
        return ResponseEntity.ok(planningHuileService.createPlanning(dto));
    }






    // GET : récupérer tous les planningHuile
    @GetMapping("/getAllPlanningHuile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<PlanningHuile> getAllPlanningHuile() {
        return planningHuileService.getAllPlanningHuile();
    }

    // GET : récupérer un planningHuile par ID
    @GetMapping("/getPlanningHuile/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public PlanningHuile getPlanningHuileById(@PathVariable Long id) {
        return planningHuileService.getPlanningHuileById(id);
    }


    @PutMapping("/PlanningHuile/{id}/check")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningHuile> checkPlanning(@PathVariable Long id) {
        PlanningHuile updated = planningHuileService.checkPlanning(id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/today")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningHuile>> getTodayPlannings() {
        return ResponseEntity.ok(planningHuileService.getPlanningHuileForToday());
    }

    @GetMapping("/date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningHuile>> getPlanningsByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(planningHuileService.getPlanningHuileForAday(date));
    }


    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningHuile>> getPlanningsBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return ResponseEntity.ok(planningHuileService.getPlanningsBetweenDates(start, end));
    }
}
