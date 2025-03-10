package com.example.haccpbackend.moduleTasks;


import com.example.haccpbackend.modulUsers.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;




    public Task createTask(Task task){ return taskRepository.save(task);}



    public List<Task> findTaskByDate(LocalDate date){

        return taskRepository.findByDateOfDone(date);
    }


    public List<Task> findTasksByStatus(String status){

        return taskRepository.findTasksByStatus(status);
    }




    public List<Task> findTasksByTaskMomentDone(String momentDone) {


        TaskMomentDone taskMomentDone = TaskMomentDone.valueOf(momentDone);
        return taskRepository.findByTaskMomentDone(taskMomentDone);

    }




    public List<Task> findTasksByTaskCategory(TaskCategory taskCategory){



        return taskRepository.findByTaskCategory(taskCategory);
    }



    public void deleteTask(Task task) { taskRepository.delete(task); }

}
