package com.example.demo.controller;

import java.util.Map;
import java.util.UUID;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.PersonResponseIdDto;
import com.example.demo.model.Nutritionist;
import com.example.demo.model.Patient;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

import jakarta.websocket.server.PathParam;

@RestController
public class PersonController {

    private PersonService personService;

    public record LoginResponse(Object data, String error) {
    }

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            Optional<Person> personOptional = Optional.ofNullable(personService.getById(id));

            byte type;

            if(personOptional.get() instanceof Patient) {
                type = 1;
            }else if(personOptional.get() instanceof Nutritionist) {
                type = 2;
            }else {
                type = 3;
            }

            return ResponseEntity.ok().body(PersonResponseIdDto.fromEntity(personOptional.get().getId(), type));
        } catch (IllegalArgumentException e) { // UUID INVALIDO
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {// pega person não encontrado e outros erros simples
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("!error!", e.getMessage()));
        } catch (Exception e) { // erros alem disso
            return ResponseEntity.internalServerError().body(
                    Map.of("!error!", "Erro interno inesperado", "*details*", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Optional<Person> loginResp = personService.login(loginRequestDto);
            
            return loginResp
                    .map(person -> ResponseEntity.ok(new LoginResponse(person.getId(), "")))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new LoginResponse(null, "E-mail e/ou senha inválidos.")));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Adiciona a imagem do paciente
    @PostMapping("/image/{id}")
    private ResponseEntity<?> addImageFromPerson(@PathParam("file") MultipartFile file,
            @PathVariable("id") String personId) {
        try {
            Long id = personService.saveImage(file, personId);
            return ResponseEntity.ok().body(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image/search/{id}")
    private ResponseEntity<?> getImageFromPerson(@PathVariable("id") String id) {
        try {
            byte[] image = personService.getImage(id);
            return ResponseEntity.ok().body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            // validar se patient tem consulta
            personService.deleteById(id);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // editar
    @PutMapping("edit/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody Person updatPerson) {
        try {
            Person updated = personService.updatePerson(id, updatPerson);
            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/ChangePassword")
    public ResponseEntity<?> updatePassword(@PathVariable("id") UUID id, @RequestBody String newPassword) {
        try {
            personService.updatePassword(id, newPassword);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }
}
