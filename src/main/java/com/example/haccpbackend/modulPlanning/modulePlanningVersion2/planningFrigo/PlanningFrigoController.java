package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/planningfrigo")
@CrossOrigin
public class PlanningFrigoController {

    private final PlanningFrigoService planningFrigoService;

    public PlanningFrigoController(PlanningFrigoService planningFrigoService) {
        this.planningFrigoService = planningFrigoService;
    }

    @PostMapping("/addPlanningFrigo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningFrigo> addPlanningFrigo(@RequestBody PlanningFrigoDTO request) {
        PlanningFrigo planning = planningFrigoService.createPlanning(request);
        return ResponseEntity.ok(planning);
    }


    // GET : récupérer tous les planningFrigo
    @GetMapping("/getAllPlanningFrigo")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<PlanningFrigo> getAllPlanningFrigo() {
        return planningFrigoService.getAllPlanningFrigo();
    }

    // GET : récupérer un planningFrigo par ID
    @GetMapping("/getPlanningFrigo/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public PlanningFrigo getPlanningFrigoById(@PathVariable Long id) {
        return planningFrigoService.getPlanningFrigoById(id);
    }


    @PutMapping("/planning-frigo/{id}/check")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningFrigo> checkPlanning(@PathVariable Long id) {
        PlanningFrigo updated = planningFrigoService.checkPlanning(id);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/today")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningFrigo>> getTodayPlannings() {
        return ResponseEntity.ok(planningFrigoService.getPlanningsForToday());
    }

    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningFrigo>> getPlanningsBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return ResponseEntity.ok(planningFrigoService.getPlanningsBetweenDates(start, end));
    }


    @GetMapping("/date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<PlanningFrigo>> getPlanningsByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(planningFrigoService.getPlanningFrigoForAday(date));
    }
}
