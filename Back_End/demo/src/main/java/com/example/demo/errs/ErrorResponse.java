package com.example.demo.errs;
//Modelo padrão para respostas de erro
import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String message,
    List<?> errors 
){
    public record FieldErrorDetail(
        String field,
        String message
    ){}
}
