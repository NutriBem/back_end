package com.example.demo.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ImageData;
import com.example.demo.repository.ImageDataRepository;


@RestController
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private ImageDataRepository imageDataRepository;

    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        try {
            ImageData imageData = new ImageData();
            imageData.setFileName(file.getOriginalFilename());
            imageData.setContentType(file.getContentType());
            imageData.setData(file.getBytes());

            imageDataRepository.save(imageData);

            return ResponseEntity.ok("teste Imagem salva com ID: " + imageData.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar imagem");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){
        Optional<ImageData> imageData = imageDataRepository.findById(id);

        return imageData.map(data -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + data.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(data.getContentType()))
                .body(data.getData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ImageData> getAllImages(){
        return imageDataRepository.findAll();
    }
}
