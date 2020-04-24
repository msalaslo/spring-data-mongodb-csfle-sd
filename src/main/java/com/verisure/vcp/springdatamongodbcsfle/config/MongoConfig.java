package com.verisure.vcp.springdatamongodbcsfle.config;

import static java.lang.String.format;
import static java.lang.System.getProperty;

import java.util.Arrays;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.internal.build.MongoDriverVersion;
import com.verisure.vcp.springdatamongodbcsfle.converter.BinaryToBsonBinaryConverter;
import com.verisure.vcp.springdatamongodbcsfle.converter.BsonBinaryToBinaryConverter;
import com.verisure.vcp.springdatamongodbcsfle.kms.LocalKMSManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableMongoRepositories(basePackages = "com.verisure.vcp.springdatamongodbcsfle.domain.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value(value = "${spring.data.mongodb.database}")
	private String DB_DATABASE;

	@Value(value = "${spring.data.mongodb.uri}")
	private String DB_CONNECTION;

//	@Autowired
//	private KMSManager kmsManager;

	@Autowired
	private LocalKMSManager kmsManager;

	@Override
	public MongoClient mongoClient() {
		kmsManager.buildOrValidateVault();
		MongoClient mongoClient = new MongoClientImpl(getMongoClientSettings(), getMongoDriverInfo());
		return mongoClient;
	}

	@Override
	protected String getDatabaseName() {
		return DB_DATABASE;
	}

	/**
	 * Returns the list of custom converters that will be used by the MongoDB
	 * template These conversions avoid exceptions caused by Spring Data MongoDB
	 * retrieving Binary types from the repository an converting them to BsonBinary
	 *
	 **/
	public CustomConversions customConversions() {
		CustomConversions customConversions = new MongoCustomConversions(
				Arrays.asList(new BinaryToBsonBinaryConverter(), new BsonBinaryToBinaryConverter()));
		return customConversions;
	}

	private MongoDriverInformation getMongoDriverInfo() {
		return MongoDriverInformation.builder().driverName(MongoDriverVersion.NAME)
				.driverVersion(MongoDriverVersion.VERSION)
				.driverPlatform(format("Java/%s/%s", getProperty("java.vendor", "unknown-vendor"),
						getProperty("java.runtime.version", "unknown-version")))
				.build();
	}

	private MongoClientSettings getMongoClientSettings() {
		return MongoClientSettings.builder().applyConnectionString(new ConnectionString(DB_CONNECTION)).build();
	}


	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context,
			BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);

		// remove _class field added by Spring Data, save storage space and avoid errors when we repackage classes 
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		// BsonBynary to Bynary and vice-versa
		try {
			mappingConverter.setCustomConversions(customConversions());
		} catch (NoSuchBeanDefinitionException ex) {
			LOGGER.error("Error setting custom conversions", ex);
		}
		return mappingConverter;
	}

}