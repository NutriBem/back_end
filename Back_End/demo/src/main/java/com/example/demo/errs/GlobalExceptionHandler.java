package com.example.demo.errs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler { //    Erros de validação do Spring (@Valid), banco inesperados e Falhas totalmente não tratadas

    // 1. Erros de validação tratados pelas suas classes *Validation
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getErrors()
            )
        );
    }

    // 2. Erros de validação do @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorResponse.FieldErrorDetail> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorResponse.FieldErrorDetail(
                error.getField(),
                error.getDefaultMessage()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos",
                errors
            )
        );
    }

    // 3. Erros de banco que escaparam das validações
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseErrors(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Violação de regra no banco de dados",
                List.of(extractDatabaseError(ex))
            )
        );
    }

    // 4. Todos os outros erros inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrors(Exception ex) {
        return ResponseEntity.internalServerError().body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno inesperado",
                null
            )
        );
    }
    //extrair e formatar mensagens erro do banco
    private ErrorResponse.FieldErrorDetail extractDatabaseError(DataIntegrityViolationException ex) {
        String mensagemOriginal = ex.getMostSpecificCause().getMessage();
        
        String mensagemAmigavel = mensagemOriginal.contains("unique constraint") || 
                                 mensagemOriginal.contains("Duplicate entry") || 
                                 mensagemOriginal.contains("UNIQUE_INDEX") || 
                                 mensagemOriginal.contains("UK_")
            ? "Registro duplicado (já existe um dado idêntico no sistema)"
            : "Registro inválido para o banco de dados";
        
        return new ErrorResponse.FieldErrorDetail("", mensagemAmigavel);
    }
}