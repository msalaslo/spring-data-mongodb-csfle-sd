package com.verisure.vcp.springdatamongodbcsfle.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO representing the error according to vnd.error specification.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("squid:S2160") // BaseDTO uses reflection for equals, so the fields of the children are covered.
public class ErrorDTO extends BaseDTO {

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
}
