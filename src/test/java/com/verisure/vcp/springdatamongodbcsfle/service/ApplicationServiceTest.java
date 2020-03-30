package com.verisure.vcp.springdatamongodbcsfle.service;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import com.verisure.vcp.springdatamongodbcsfle.domain.repository.ApplicationRepository;
import com.verisure.vcp.springdatamongodbcsfle.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService = new ApplicationServiceImpl();

    @Mock
    private ApplicationRepository applicationRepository;


    @Test
    void findAll() {
        when(applicationRepository.findAll()).thenReturn(Arrays.asList(ApplicationItem.builder().itemDescription("Description").build()));
        List<ApplicationItem> items = applicationService.getApplicationItems();
        assertEquals(items.size(), 1);
        verify(applicationRepository, times(1)).findAll();
    }


    @ParameterizedTest(name = "Application Item  description: {0}, code: {1}")
    @CsvSource({
            "Description,    code",
            "Description 2,  code 2",
            "Description 3,  code 3",
            "Description 4, code 4"
    })
    void exampleParametrized(String description, String code) {
        ApplicationItem item = ApplicationItem.builder()
                .itemDescription(description)
                .itemCode(code)
                .build();
        when(applicationRepository.findAll()).thenReturn(Arrays.asList(item));
        List<ApplicationItem> items = applicationService.getApplicationItems();
        assertEquals(items.size(), 1);
        assertEquals(items.get(0), item,
                () -> description + " should equal " + item.getItemDescription());

        verify(applicationRepository, times(1)).findAll();
    }

}
