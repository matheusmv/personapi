package com.github.matheusmv.personapi.controller;

import com.github.matheusmv.personapi.controller.docs.PersonControllerDocs;
import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable Long id) {
        var personDTO = personService.getById(id);

        return ResponseEntity.ok().body(personDTO);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createAPerson(@RequestBody @Valid PersonDTO personDTO) {
        var createdPerson = personService.create(personDTO);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updateAPerson(@PathVariable Long id,
                                                   @RequestBody PersonDTO personDTO) {
        var updatedPerson = personService.update(id, personDTO);

        return ResponseEntity.ok().body(updatedPerson);
    }
}
