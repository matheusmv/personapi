package com.github.matheusmv.personapi.controller;

import com.github.matheusmv.personapi.exception.ResourceNotFoundException;
import com.github.matheusmv.personapi.service.PersonService;
import com.github.matheusmv.personapi.utils.PersonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.github.matheusmv.personapi.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String PERSON_API_URL_PATH = "/api/v1/people";
    private static final long INVALID_PERSON_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenGETListWithPersonsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        var personDTO = PersonUtils.toPersonDTO();

        // when
        when(personService.listAll()).thenReturn(Collections.singletonList(personDTO));

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(personDTO.getLastName())))
                .andExpect(jsonPath("$[0].cpf", is(personDTO.getCpf())))
                .andExpect(jsonPath("$[0].birthDate", is(personDTO.getBirthDate())))
                .andExpect(jsonPath("$[0].phones[0].type", is(personDTO.getPhones().get(0).getType().toString())))
                .andExpect(jsonPath("$[0].phones[0].number", is(personDTO.getPhones().get(0).getNumber())));
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        var personDTO = PersonUtils.toPersonDTO();

        // when
        when(personService.getById(personDTO.getId())).thenReturn(personDTO);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/" + personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(personDTO.getLastName())))
                .andExpect(jsonPath("$.cpf", is(personDTO.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(personDTO.getBirthDate())))
                .andExpect(jsonPath("$.phones[0].type", is(personDTO.getPhones().get(0).getType().toString())))
                .andExpect(jsonPath("$.phones[0].number", is(personDTO.getPhones().get(0).getNumber())));
    }

    @Test
    void whenGETIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ResourceNotFoundException.class).when(personService).getById(INVALID_PERSON_ID);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/" + INVALID_PERSON_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPOSTIsCalledThenAPersonIsCreated() throws Exception {
        // given
        var personDTO = PersonUtils.toPersonDTO();

        // when
        when(personService.create(personDTO)).thenReturn(personDTO);

        // then
        mockMvc.perform(post(PERSON_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(personDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(personDTO.getLastName())))
                .andExpect(jsonPath("$.cpf", is(personDTO.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(personDTO.getBirthDate())))
                .andExpect(jsonPath("$.phones[0].type", is(personDTO.getPhones().get(0).getType().toString())))
                .andExpect(jsonPath("$.phones[0].number", is(personDTO.getPhones().get(0).getNumber())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        var personDTO = PersonUtils.toPersonDTO();
        personDTO.setCpf(null);

        // then
        mockMvc.perform(post(PERSON_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(personDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledThenAPersonIsUpdated() throws Exception {
        // given
        var personDetailsDTO = PersonUtils.toPersonDTO();
        var expectedUpdatedPersonDTO = PersonUtils.toPersonDTO();

        // when
        when(personService.update(expectedUpdatedPersonDTO.getId(), personDetailsDTO)).thenReturn(personDetailsDTO);

        // then
        mockMvc.perform(put(PERSON_API_URL_PATH + "/" + expectedUpdatedPersonDTO.getId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(personDetailsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(expectedUpdatedPersonDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUpdatedPersonDTO.getLastName())))
                .andExpect(jsonPath("$.cpf", is(expectedUpdatedPersonDTO.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(expectedUpdatedPersonDTO.getBirthDate())));
    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenAnErrorIsReturned() throws Exception {
        // given
        var personDetailsDTO = PersonUtils.toPersonDTO();

        // when
        doThrow(ResourceNotFoundException.class).when(personService).update(INVALID_PERSON_ID, personDetailsDTO);

        // then
        mockMvc.perform(put(PERSON_API_URL_PATH + "/" + INVALID_PERSON_ID)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(personDetailsDTO)))
                .andExpect(status().isNotFound());
    }
}
