package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, Long>{
    Optional<ImageData> findById(Long id);
}
