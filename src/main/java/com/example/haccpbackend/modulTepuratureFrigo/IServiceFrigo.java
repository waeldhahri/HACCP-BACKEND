package com.example.haccpbackend.modulTepuratureFrigo;

import org.springframework.web.multipart.MultipartFile;

public interface IServiceFrigo {





        public Frigo updateFrigo(Long id , FrigoRequest frigoRequest , MultipartFile file);




        public void deleteFrigo(Frigo frigo);




}
