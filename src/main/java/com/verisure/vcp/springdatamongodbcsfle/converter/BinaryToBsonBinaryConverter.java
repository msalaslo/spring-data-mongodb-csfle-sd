package com.verisure.vcp.springdatamongodbcsfle.converter;

import org.bson.BsonBinary;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

/**
 * Custom converter to avoid conversion errors retrieving domain object in Spring MongoDB
 * 
 * Exception in thread "main" org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [org.bson.types.Binary] to type [org.bson.BsonBinary]
	at org.springframework.core.convert.support.GenericConversionService.handleConverterNotFound(GenericConversionService.java:321)
	
 * @author Miguel
 *
 */
public class BinaryToBsonBinaryConverter implements Converter<Binary, BsonBinary> {
	@Override
	public BsonBinary convert(Binary source) {
		return new BsonBinary(source.getData());
	}
}
