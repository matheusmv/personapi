package com.github.matheusmv.personapi.controller.docs;

import com.github.matheusmv.personapi.dto.PersonDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api("Endpoints to manage people")
public interface PersonControllerDocs {

    @ApiOperation(value = "Returns a list of all persons registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all persons registered in the system"),
    })
    ResponseEntity<List<PersonDTO>> listPersons();

    @ApiOperation(value = "Returns person found by a given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, person found in the system"),
            @ApiResponse(code = 404, message = "Failure, person with given id not found.")
    })
    ResponseEntity<PersonDTO> findPersonById(Long id);

    @ApiOperation(value = "Person creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success, person created"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ResponseEntity<PersonDTO> createAPerson(PersonDTO personDTO);
}
