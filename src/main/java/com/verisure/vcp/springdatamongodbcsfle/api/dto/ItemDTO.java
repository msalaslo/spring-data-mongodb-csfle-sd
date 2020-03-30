package com.verisure.vcp.springdatamongodbcsfle.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sample DTO object. <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO extends BaseDTO {

    @Schema(description = "Application item code", required = true)
    private String applicationCode;

    @Schema(description = "Application description", required = true)
    private String applicationDescription;

}
