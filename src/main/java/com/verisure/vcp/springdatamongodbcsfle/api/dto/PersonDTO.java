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
public class PersonDTO extends BaseDTO {

    @Schema(description = "Id", required = false)
	private String id;
    
    @Schema(description = "name", required = false)
	private String name;
    
    @Schema(description = "age", required = false)
	private int age;
    
    @Schema(description = "dni", required = false)
	private String dni;

}
