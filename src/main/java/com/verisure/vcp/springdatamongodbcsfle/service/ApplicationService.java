package com.verisure.vcp.springdatamongodbcsfle.service;


import com.verisure.vcp.springdatamongodbcsfle.domain.entity.ApplicationItem;
import java.util.List;

/**
 * Sample service interface used as template. <b>Please remove for actual project implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
public interface ApplicationService {

	/**
	 * Creates an application item.
	 * 
	 * @param applicationItem The application item to create.
	 */
    void createApplicationItem(ApplicationItem applicationItem);

	/**
	 * Gets all the application items.
	 * 
	 * @return The list of application items.
	 */
    List<ApplicationItem> getApplicationItems();

}
