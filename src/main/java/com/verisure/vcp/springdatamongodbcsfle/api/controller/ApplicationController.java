package com.verisure.vcp.springdatamongodbcsfle.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.verisure.vcp.springdatamongodbcsfle.api.converter.ItemConverter;
import com.verisure.vcp.springdatamongodbcsfle.api.dto.ItemDTO;
import com.verisure.vcp.springdatamongodbcsfle.service.ApplicationService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;



/**
 * Sample controller used as template. <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@RestController
@RequestMapping("/application")
@Tag(name = "Application demo")
public class ApplicationController {

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private ApplicationService applicationService;


    @GetMapping(produces = "application/json")
    @ResponseBody
    @Operation(
            description = "view the list of ALL application items",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemDTO.class))), responseCode = "200")
            }
    )
    public List<ItemDTO> getItems() {
        LOGGER.debug("getItems::Trying to retrieve all items");
        return applicationService.getApplicationItems().stream()
                .map(itemConverter::toItemDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/delay/{seconds}")
    @Operation(
            description = "view the list of ALL application items with delay",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemDTO.class))), responseCode = "200")
            }
    )
    public List<ItemDTO> getItemsWithDelay(@PathVariable("seconds") final int seconds) throws InterruptedException {
        LOGGER.debug("getItems::Trying to retrieve all items with delay");
        TimeUnit.SECONDS.sleep(seconds);
        return applicationService.getApplicationItems().stream()
                .map(itemConverter::toItemDto)
                .collect(Collectors.toList());
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            description = "Create an application item"
    )
    public void createAlert(@Valid @RequestBody ItemDTO request) {
        LOGGER.debug("Creating an item: {}", request.toString());
        applicationService.createApplicationItem(itemConverter.toApplicationItem(request));
    }

}
