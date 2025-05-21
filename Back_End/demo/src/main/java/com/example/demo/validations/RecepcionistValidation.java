package com.example.demo.validations;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;

@Component
public class RecepcionistValidation extends Validation {

     private RecepcionistRepository recepcionistRepository;

     public RecepcionistValidation(RecepcionistRepository recepcionistRepository) {
          this.recepcionistRepository = recepcionistRepository;
     }

     public Recepcionist getById(String id) {
          UUID idToUuid = UUID.fromString(id);

          Optional<Recepcionist> receptionisOptional = recepcionistRepository.findById(idToUuid);

          if (receptionisOptional.isPresent())
               return receptionisOptional.get();

          throw new IllegalArgumentException("Recepcionista não encontrado");
     }
}
