package com.verisure.vcp.springdatamongodbcsfle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import com.verisure.vcp.springdatamongodbcsfle.domain.repository.ApplicationRepository;
import com.verisure.vcp.springdatamongodbcsfle.service.ApplicationService;

/**
 * Sample service implementation.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Service
//@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public void createApplicationItem(ApplicationItem applicationItem) {
        applicationRepository.save(applicationItem);
    }

    @Override
    public List<ApplicationItem> getApplicationItems() {
      return applicationRepository.findAll();
    }

}
