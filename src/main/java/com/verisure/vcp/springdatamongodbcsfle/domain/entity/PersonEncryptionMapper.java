package com.verisure.vcp.springdatamongodbcsfle.domain.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bson.BsonBinary;
import org.bson.BsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.vault.EncryptOptions;
import com.verisure.vcp.springdatamongodbcsfle.kms.LocalKMSManager;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PersonEncryptionMapper {

//	@Autowired
//	protected KMSManager kmsManager;

	@Autowired
	protected LocalKMSManager kmsManager;
	
	// Apply deterministic encryption for searchable attributes
	public static final String DETERMINISTIC_ENCRYPTION_TYPE = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";

	// Apply radom encryption for non searchable attributes and add more security
	public static final String RANDOM_ENCRYPTION_TYPE = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";

	public EncryptedPerson getEncrypedPerson(Person p) {
		EncryptedPerson ep = new EncryptedPerson();
		ep.setName(p.getName());
		ep.setAge(p.getAge());
		ep.setDni(kmsManager.getClientEncryption().encrypt(new BsonString(p.getDni()),
				getEncryptOptions(DETERMINISTIC_ENCRYPTION_TYPE)));
		ep.setId(p.getId());
		return ep;
	}

	public Person getPerson(EncryptedPerson ep, boolean encrypted) {
		String dni = kmsManager.getClientEncryption().decrypt(ep.getDni()).asString().getValue();
		//If encrypted we return the hash of the string in hexadecimal formal
		if (encrypted) {
			dni = getHexadecimalHashString(dni);
		}
		Person p = new Person();
		p.setName(ep.getName());
		p.setAge(ep.getAge());
		p.setDni(dni);
		p.setId(ep.getId());
		return p;
	}

	public BsonBinary getEncryptedDni(String dni) {
		return kmsManager.getClientEncryption().encrypt(new BsonString(dni),
				getEncryptOptions(DETERMINISTIC_ENCRYPTION_TYPE));
	}

	private EncryptOptions getEncryptOptions(String algorithm) {
		EncryptOptions encryptOptions = new EncryptOptions(algorithm);
		encryptOptions.keyId(new BsonBinary(kmsManager.getEncryptionKeyUUID()));
		return encryptOptions;
	}

	private String getHexadecimalHashString(String originalString) {
		String hash = null;
		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
			hash = bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			// TODO throw exception
			LOGGER.error(e.getMessage());
		}
		return hash;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
