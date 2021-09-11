package com.github.matheusmv.personapi.service;

import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.dto.mapper.PersonMapper;
import com.github.matheusmv.personapi.entity.Person;
import com.github.matheusmv.personapi.exception.CPFAlreadyRegisteredException;
import com.github.matheusmv.personapi.exception.DatabaseException;
import com.github.matheusmv.personapi.exception.ResourceNotFoundException;
import com.github.matheusmv.personapi.repository.PersonRepository;
import com.github.matheusmv.personapi.service.util.CPFValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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

    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        verifyIfCpfIsAlreadyRegistered(personDTO.getCpf());

        var person = personMapper.toModel(personDTO);
        var savedPerson = personRepository.save(person);

        return personMapper.toDTO(savedPerson);
    }

    private void verifyIfCpfIsAlreadyRegistered(String cpf) {
        var person = personRepository.findByCpf(cpf);

        if (person.isPresent()) {
            throw new CPFAlreadyRegisteredException(String.format("CPF %s already registered in the system.", cpf));
        }
    }

    @Transactional
    public PersonDTO update(Long personId, PersonDTO personDetails) {
        var person = personMapper.toModel(getById(personId));

        updatePersonDetails(person, personDetails);

        person = personRepository.save(person);

        return personMapper.toDTO(person);
    }

    // TODO: update phones
    // TODO: test CPFValidation
    private void updatePersonDetails(Person person, PersonDTO personDetails) {
        Optional.ofNullable(personDetails)
                .filter(Predicate.not(Objects::isNull))
                .ifPresent(personDTO -> {
                    Optional.ofNullable(personDTO.getFirstName())
                            .filter(Predicate.not(String::isBlank))
                            .ifPresent(person::setFirstName);

                    Optional.ofNullable(personDTO.getLastName())
                            .filter(Predicate.not(String::isBlank))
                            .ifPresent(person::setLastName);

                    Optional.ofNullable(personDTO.getCpf())
                            .filter(Predicate.not(String::isBlank).and(CPFValidation::isValidCPF))
                            .ifPresent(person::setCpf);

                    Optional.ofNullable(personDTO.getBirthDate())
                            .filter(Predicate.not(String::isBlank))
                            .map(LocalDate::parse)
                            .ifPresent(person::setBirthDate);
                });
    }

    public void delete(Long personId) {
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("No person found with given id: " + personId));

        try {
            personRepository.delete(person);
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
