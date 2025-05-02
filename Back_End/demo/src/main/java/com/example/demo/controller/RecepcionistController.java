package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.service.RecepcionistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("recepcionist")
public class RecepcionistController {
    
    private RecepcionistRepository recepcionistRepository;
    private RecepcionistService recepcionistService;

    public RecepcionistController(RecepcionistRepository recepcionistRepository, RecepcionistService recepcionistService){
        this.recepcionistRepository = recepcionistRepository;
        this.recepcionistService = recepcionistService;        
    }

    @PostMapping
    public void postMethodName(@RequestBody Recepcionist recepcionist) {
        recepcionistService.addRecepcionist(recepcionist);
    }
    
}
