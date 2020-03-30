package com.verisure.vcp.springdatamongodbcsfle.api.controller;

import com.verisure.vcp.springdatamongodbcsfle.api.converter.ItemConverter;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import com.verisure.vcp.springdatamongodbcsfle.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ApplicationControllerTest {

    @Mock
    ApplicationService applicationService;

    @Mock
    ItemConverter itemConverter;

    @InjectMocks
    ApplicationController applicationController;

    MockMvc mockMvc;

    ApplicationItem applicationItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
        applicationItem.builder()
                .itemCode("ITEM_CODE")
                .itemDescription("ITEM DESCRIPTION")
                .build();
    }

    @Test
    void getAllItemsOK() throws Exception {
        given(applicationService.getApplicationItems()).willReturn(Arrays.asList(applicationItem));
        mockMvc.perform(get("/application"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

}
