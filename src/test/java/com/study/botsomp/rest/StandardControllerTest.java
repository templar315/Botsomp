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
            .designation("GOST8240-89")
            .build();

    private StandardDTO standard2 = StandardDTO.builder()
            .designation("GOST8509-93")
            .build();

    @Before
    public void setUp() throws Exception {
        when(standardService.add(standard)).thenReturn(standard);
        when(standardService.update(standard)).thenReturn(standard);
        when(standardService.getOne(standard.getId())).thenReturn(standard);
        when(standardService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(standard, standard2)));
        when(standardService.delete(standard.getId())).thenReturn(true);

    }

    @Test
    public void add() throws Exception {
        String requestBody = objectMapper.writeValueAsString(standard);

        mvc.perform(post(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(requestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(standard);

        mvc.perform(put(STANDARDS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(STANDARDS + "/" + standard.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void getOne() throws Exception {
        mvc.perform(get(STANDARDS + "/" + standard.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(get(STANDARDS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(standard, standard2))));
    }

}
