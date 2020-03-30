package com.verisure.vcp.springdatamongodbcsfle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Application bootstrap class.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@SpringBootApplication
public class Application {
	
    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";
    
    
	
    protected Application() {
        LOGGER.info("Starting REST microservice");
    }

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

    /**
     * TODO: If your application uses: 
     * - Spring Rest Template: this method is intented to propagate tracing fields with Spring Sleuth,
     *   you have to Autowire this RestTemplate bean in your clients. 
     * - Spring Feign (recommended): delete this method.
     * 
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }
}
