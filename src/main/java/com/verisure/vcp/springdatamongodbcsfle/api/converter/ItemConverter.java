package com.verisure.vcp.springdatamongodbcsfle.api.converter;

import com.verisure.vcp.springdatamongodbcsfle.api.dto.ItemDTO;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Sample converter used as template. <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Mapper(componentModel = "spring")
public interface ItemConverter {

    @Mappings({
            @Mapping(source = "itemCode", target = "applicationCode"),
            @Mapping(source = "itemDescription", target = "applicationDescription")
    })
    ItemDTO toItemDto(ApplicationItem item);

    @Mappings({
            @Mapping(source = "applicationCode", target = "itemCode"),
            @Mapping(source = "applicationDescription", target = "itemDescription")
    })
    ApplicationItem toApplicationItem(ItemDTO entry);

}