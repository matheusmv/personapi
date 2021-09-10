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
}
