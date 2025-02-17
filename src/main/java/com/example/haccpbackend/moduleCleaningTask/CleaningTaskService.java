package com.example.haccpbackend.moduleCleaningTask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CleaningTaskService {

    private static final String IMAGE_DIR = "uploads/cleaning/";

    @Autowired
    private CleaningTaskRepository repository;



    public CleaningTask saveTask(CleaningTask task, MultipartFile before, MultipartFile after) throws IOException {
        if (before != null && !before.isEmpty()) {
            task.setBeforeImage(storeFile(before));
        }
        if (after != null && !after.isEmpty()) {
            task.setAfterImage(storeFile(after));
        }
        return repository.save(task);
    }

    private byte[] storeFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(IMAGE_DIR));
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_DIR, fileName);
        Files.write(filePath, file.getBytes());
        return filePath.toString().getBytes();
    }

    public List<CleaningTask> getWeeklyReport() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(7);
        return repository.findByTaskDateBetween(start, end);
    }







}
