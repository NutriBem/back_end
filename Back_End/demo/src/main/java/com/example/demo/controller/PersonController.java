package com.example.demo.controller;

import java.util.UUID;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

    private PersonService personService;
    public record LoginResponse(Object data, String error) {}

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        try {

            var patientOptional = personService.getById(id);

            if (patientOptional.isPresent())
                return ResponseEntity.ok(patientOptional.get());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR:" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try {
            Optional<Person> loginResp = personService.login(loginRequestDto);
            
            return loginResp
            .map(person -> ResponseEntity.ok(new LoginResponse(person.getId(), null)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(null, "E-mail e/ou senha inválidos.")));
                
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            if (personService.deleteById(id))
                return ResponseEntity.ok("Sucess!");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

    //editar
    @PutMapping("edit/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody Person updatPerson){
        try {
            Person updated = personService.updatePerson(id, updatPerson);
            return ResponseEntity.ok(updated);
        } 
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/ChangePassword")
    public ResponseEntity<?> updatePassword(@PathVariable("id") UUID id, @RequestBody String newPassword){
        try {
            personService.updatePassword(id, newPassword);
            return ResponseEntity.ok("Senha alterada com sucesso");   
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }
}
