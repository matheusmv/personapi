package com.github.matheusmv.personapi.service;

import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.dto.mapper.PersonMapper;
import com.github.matheusmv.personapi.exception.ResourceNotFoundException;
import com.github.matheusmv.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional(readOnly = true)
    public List<PersonDTO> listAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonDTO getById(Long personId) {
        return personRepository.findById(personId)
                .map(personMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No person found with given id: " + personId));
    }
}
