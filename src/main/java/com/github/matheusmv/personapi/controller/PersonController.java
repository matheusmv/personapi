package com.github.matheusmv.personapi.controller;

import com.github.matheusmv.personapi.controller.docs.PersonControllerDocs;
import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController implements PersonControllerDocs {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> listPersons() {
        var listOfPersons = personService.listAll();

        return ResponseEntity.ok().body(listOfPersons);
    }
}
