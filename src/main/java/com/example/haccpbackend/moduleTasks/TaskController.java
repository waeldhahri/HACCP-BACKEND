package com.example.haccpbackend.moduleTasks;


import com.example.haccpbackend.modulProducts.Product;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    @GetMapping("/page")
    public Page<Task> getAllTasks(Pageable pageable){


        return taskRepository.findAllByOrderByIdDesc(pageable);

    }

    @PostMapping("")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){


        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));

    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Task>> findTaskByDate(@PathVariable LocalDate date){

        return ResponseEntity.ok(taskService.findTaskByDate(date));
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> findTasksByStatus(@PathVariable String status){

        return ResponseEntity.ok(taskService.findTasksByStatus(status));
    }

    @GetMapping("/taskMomentDone/{taskMomentDone}")
    public ResponseEntity<List<Task>> findTasksByTaskMomentDone(@PathVariable String taskMomentDone){

        return ResponseEntity.ok(taskService.findTasksByTaskMomentDone(taskMomentDone));

    }


    @GetMapping("taskCategory/{taskCategory}")
    public ResponseEntity<List<Task>> findTasksByTaskCategory(@PathVariable TaskCategory taskCategory){

        return ResponseEntity.ok(taskService.findTasksByTaskCategory(taskCategory));
    }






    @DeleteMapping("/{taskId}")
    @Transactional
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){

        Optional<Task> optionalTask= taskRepository.findById(taskId);


        if (optionalTask.isPresent()){

            taskService.deleteTask(optionalTask.get());
            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }

    }




}
