package com.example.haccpbackend.moduleCleaningTask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cleaning-tasks")
public class CleaningTaskController {

    @Autowired
    private CleaningTaskService service;

    @PostMapping("/add")
    public ResponseEntity<CleaningTask> addTask(@RequestPart("task") CleaningTask task,
                                                @RequestPart(value = "beforeImage", required = false) MultipartFile before,
                                                @RequestPart(value = "afterImage", required = false) MultipartFile after) {
        try {
            return ResponseEntity.ok(service.saveTask(task, before, after));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/weekly-report")
    public ResponseEntity<List<CleaningTask>> getWeeklyReport() {
        return ResponseEntity.ok(service.getWeeklyReport());
    }


}
