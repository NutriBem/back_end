package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, Long>{
    
}
