package com.study.botsomp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.botsomp.dto.SteelGradeDTO;
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
@WebMvcTest(SteelGradeController.class)
@ComponentScan
public class SteelGradeControllerTest {

    private final String STEEL_GRADES = "/grades";

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

    SteelGradeDTO steelGrade = SteelGradeDTO.builder()
            .designation("St5sp")
            .gradeStandard("GOST380-2005")
            .build();

    SteelGradeDTO steelGradeOut = SteelGradeDTO.builder()
            .id(1L)
            .designation("St5sp")
            .gradeStandard("GOST380-2005")
            .build();

    SteelGradeDTO steelGrade2 = SteelGradeDTO.builder()
            .id(2L)
            .designation("St3sp")
            .gradeStandard("GOST380-2005")
            .build();

    @Test
    public void add() throws Exception {
        when(steelGradeService.add(steelGrade)).thenReturn(steelGradeOut);
        mvc.perform(post(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(steelGradeOut)));
    }

    @Test
    public void addWithId() throws Exception {
        mvc.perform(post(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWithValidationViolation() throws Exception {
        steelGrade.setDesignation("");
        mvc.perform(post(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        when(steelGradeService.update(steelGrade2)).thenReturn(steelGrade2);
        String contactsRequestBody = objectMapper.writeValueAsString(steelGrade2);
        mvc.perform(put(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void updateNonExistEntity() throws Exception {
        when(steelGradeService.update(steelGrade2)).thenReturn(null);
        mvc.perform(put(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade2)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWithoutId() throws Exception {
        mvc.perform(put(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidationViolation() throws Exception {
        steelGrade2.setDesignation("");
        mvc.perform(put(STEEL_GRADES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(steelGrade2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        when(steelGradeService.delete(steelGrade.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete(STEEL_GRADES + "/" + steelGrade.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getOne() throws Exception {
        when(steelGradeService.getOne(steelGrade.getId())).thenReturn(steelGrade);
        mvc.perform(get(STEEL_GRADES + "/" + steelGrade.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        when(steelGradeService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(steelGradeOut, steelGrade2)));
        mvc.perform(get(STEEL_GRADES)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(steelGradeOut, steelGrade2))));
    }

}
