package com.study.botsomp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.botsomp.dto.StandardDTO;
import com.study.botsomp.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StandardController.class)
@ComponentScan
public class StandardControllerTest {

    private final String STANDARDS = "/standards";

    @MockBean
    private ContactDetailsService contactDetailsService;

    @MockBean
    private ProductService productService;

    @MockBean
    private StandardService standardService;

    @MockBean
    private ManufacturerService manufacturerService;

    @MockBean
    private SteelGradeService steelGradeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private StandardDTO standard = StandardDTO.builder()
            .id(0)
            .designation("GOST8240-89")
            .build();
    private StandardDTO standardOut = StandardDTO.builder()
            .id(1L)
            .designation("GOST8240-89")
            .build();

    private StandardDTO standard2 = StandardDTO.builder()
            .id(2L)
            .designation("GOST8509-93")
            .build();

    @Test
    public void add() throws Exception {
        when(standardService.add(standard)).thenReturn(standardOut);
        mvc.perform(post(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(standardOut)));
    }

    @Test
    public void addWithId() throws Exception {
        mvc.perform(post(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWithValidationViolation() throws Exception {
        standard2.setDesignation("");
        mvc.perform(post(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        when(standardService.update(standard2)).thenReturn(standard2);
        String contactsRequestBody = objectMapper.writeValueAsString(standard2);
        mvc.perform(put(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void updateNonExistEntity() throws Exception {
        when(standardService.update(standard2)).thenReturn(null);
        mvc.perform(put(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard2)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWithoutId() throws Exception {
        mvc.perform(put(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidationViolation() throws Exception {
        standard2.setDesignation("");
        mvc.perform(put(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standard2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        when(standardService.delete(standard.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete(STANDARDS + "/" + standard.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getOne() throws Exception {
        when(standardService.getOne(standard.getId())).thenReturn(standard);
        mvc.perform(get(STANDARDS + "/" + standard.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        when(standardService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(standardOut, standard2)));
        mvc.perform(get(STANDARDS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(standardOut, standard2))));
    }

}
