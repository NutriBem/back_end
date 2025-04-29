package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("recepcionist")
public class RecepcionistController {
    
    private RecepcionistRepository recepcionistRepository;
    

}
