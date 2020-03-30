package com.verisure.vcp.springdatamongodbcsfle.domain.repository;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Sample repository used for demonstration purposes only.  Ideally Spring Data SHOULD be used for building repositories.
 * <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@Repository
public class ApplicationRepository {

    public List<ApplicationItem> findAll() {

        LOGGER.debug("Retrieving the whole list of application items!");
        ApplicationItem applicationItem = ApplicationItem.builder()
                .itemCode(UUID.randomUUID().toString())
                .itemDescription("ITEM")
                .build();
        return Arrays.asList(applicationItem);
    }

    public void save(ApplicationItem applicationItem) {

        LOGGER.debug("Application item with ID {} added", applicationItem.getItemCode());
    }

}
