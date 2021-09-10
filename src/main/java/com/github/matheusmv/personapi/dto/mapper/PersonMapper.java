package com.github.matheusmv.personapi.dto.mapper;

import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}
