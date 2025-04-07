package com.example.haccpbackend.modulTepuratureFrigo;


import com.example.haccpbackend.moduleTasks.Task;
import com.example.haccpbackend.moduleTasks.TaskCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrigoService {



    private final FrigoRepository frigoRepository;

    public FrigoService(FrigoRepository frigoRepository) {
        this.frigoRepository = frigoRepository;
    }






    public List<Frigo> findFrigoByCategorie(String categorieName){

        return frigoRepository.findByCategorie_NameIgnoreCase(categorieName);
    }




}
