package com.verisure.vcp.springdatamongodbcsfle.api.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract Data Transfer Object (DTO) to be used as the parent of all DTOs in the API layer. It contains the hashCode,
 * toString and equals methods making use of the Apache Commons Lang3 library through its builder classes.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
public class BaseDTO {
    protected BaseDTO() {
        // Do not instance this class. Only children of it should be instanced.
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}