package com.verisure.vcp.springdatamongodbcsfle.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Sample domain entity. <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private String itemCode;

  private String itemDescription;
    
}
