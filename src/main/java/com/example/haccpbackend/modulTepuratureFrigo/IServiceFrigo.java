package com.example.haccpbackend.modulTepuratureFrigo;

import com.example.haccpbackend.modulProducts.Product;
import com.example.haccpbackend.modulProducts.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceFrigo {





        public Frigo updateFrigo(Long id , FrigoRequest frigoRequest , MultipartFile file);




        public void deleteFrigo(Frigo frigo);




}
