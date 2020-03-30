package com.verisure.vcp.springdatamongodbcsfle.client.impl;

import com.verisure.vcp.springdatamongodbcsfle.api.dto.ItemDTO;
import com.verisure.vcp.springdatamongodbcsfle.client.RestTemplateClientExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Sample Spring Rest Template Implementation client used as template. <b>Please remove for actual project implementation.</b>
 *
 * @since 3.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Service
public class RestTemplateClientExampleImpl implements RestTemplateClientExample {

	@Autowired
	RestTemplate restTemplate;

	public List<ItemDTO> getRemoteItems() {
		String url  = "http://localhost:8080/application";
		return getItemsRemote(url);
	}

	@Override
	public List<ItemDTO> getRemoteItemsWithDelay(int delay) {
		String url  = "http://localhost:8080/application/delay/"+ delay;
		return getItemsRemote(url);
	}

	private List<ItemDTO> getItemsRemote(String url) {
		ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<ItemDTO>>(){});
		List<ItemDTO> items = response.getBody();
		return items;
	}
}
