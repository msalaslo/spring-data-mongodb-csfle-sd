package com.verisure.vcp.springdatamongodbcsfle.api.controller;

import java.util.List;

import com.verisure.vcp.springdatamongodbcsfle.client.RestTemplateClientExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.verisure.vcp.springdatamongodbcsfle.api.converter.ItemConverter;
import com.verisure.vcp.springdatamongodbcsfle.api.dto.ItemDTO;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Sample controller used as template.
 * Used to invoke from the Spring Feign and Rest Template clients. 
 * <b>Please remove for actual project implementation.</b>
 *
 * @since 3.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@RestController
@RequestMapping("/remote")
@Tag(name = "Remote application demo")
public class RemoteController {

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private RestTemplateClientExample restClient;
    
    @GetMapping(produces = "application/json")
    @ResponseBody
    @Operation(
            description = "view the list of ALL application items",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemDTO.class))), responseCode = "200")
            }
    )
    public List<ItemDTO> getItems(@Parameter(hidden = true)  @RequestHeader HttpHeaders headers) {
        LOGGER.debug("Remote application controller invoked:" + headers);
        return restClient.getRemoteItems();
    }
}
