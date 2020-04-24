package com.verisure.vcp.springdatamongodbcsfle.kms;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.ClientEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.client.vault.ClientEncryptions;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KMSManager {

	@Value(value = "${spring.data.mongodb.uri}")
	private String DB_CONNECTION;
	
	@Value(value = "${spring.data.mongodb.key.vault.database}")
	private String KEY_VAULT_DATABASE;
	
	@Value(value = "${spring.data.mongodb.key.vault.collection}")
	private String KEY_VAULT_COLLECTION;
	
	@Value(value = "${spring.data.mongodb.key.vault.name}")
	private String KEY_NAME;

	@Value(value = "${spring.data.mongodb.remoteKmsProvider}")
	private String REMOTE_KMS_PROVIDER;
	
	@Value(value = "${spring.data.mongodb.encryption.kms.region}")
	private String KMS_AWS_REGION;

	@Value(value = "${spring.data.mongodb.encryption.kms.accessKey}")
	private String KMS_AWS_ACCESS_KEY;

	@Value(value = "${spring.data.mongodb.encryption.kms.secretKey}")
	private String KMS_AWS_SECRET_ACCESS_KEY;
	
	@Value(value = "${spring.data.mongodb.encryption.kms.masterKeyARN}")
	private String KMS_AWS_MASTER_KEY_ARN;
	
	private ClientEncryption clientEncryption;
	
	private String encryptionKeyBase64;
	private UUID encryptionKeyUUID;

	public String getEncryptionKeyBase64() {
		return encryptionKeyBase64;
	}

	public UUID getEncryptionKeyUUID() {
		return encryptionKeyUUID;
	}
	
	public void buildOrValidateVault() {
		if (doesEncryptionKeyExist()) {
			return;
		}
		BsonString masterKeyRegion = new BsonString(KMS_AWS_REGION); // e.g. "us-east-2"
		BsonString masterKeyArn = new BsonString(KMS_AWS_MASTER_KEY_ARN); // e.g. "arn:aws:kms:us-east-2:111122223333:alias/test-key"
		DataKeyOptions dataKeyOptions = new DataKeyOptions().masterKey(
		    new BsonDocument()
		        .append("region", masterKeyRegion)
		        .append("key", masterKeyArn));
		//To validate that the key exists in doesEncryptionKeyExist
		dataKeyOptions.keyAltNames(Arrays.asList(KEY_NAME));	
		BsonBinary dataKeyId = getClientEncryption().createDataKey(REMOTE_KMS_PROVIDER, dataKeyOptions);

		this.encryptionKeyUUID = dataKeyId.asUuid();
		LOGGER.debug("DataKeyID [UUID]{}", dataKeyId.asUuid());

		String base64DataKeyId = Base64.getEncoder().encodeToString(dataKeyId.getData());
		this.encryptionKeyBase64 = base64DataKeyId;
		LOGGER.debug("DataKeyID [base64]: {}", base64DataKeyId);
	}
	
	public ClientEncryption getClientEncryption() {
		if(clientEncryption == null) {
			String keyVaultNamespace = KEY_VAULT_DATABASE + "." + KEY_VAULT_COLLECTION;
			ClientEncryptionSettings clientEncryptionSettings = ClientEncryptionSettings.builder()
					.keyVaultMongoClientSettings(MongoClientSettings.builder()
							.applyConnectionString(new ConnectionString(DB_CONNECTION)).build())
					.keyVaultNamespace(keyVaultNamespace).kmsProviders(this.getKMSMap()).build();
	
			clientEncryption = ClientEncryptions.create(clientEncryptionSettings);
		}
		return clientEncryption;
	}

	private boolean doesEncryptionKeyExist() {
		MongoClient mongoClient = MongoClients.create(DB_CONNECTION);
		MongoCollection<Document> collection = mongoClient.getDatabase(KEY_VAULT_DATABASE)
				.getCollection(KEY_VAULT_COLLECTION);
		Bson query = Filters.in("keyAltNames", KEY_NAME);
		Document doc = collection.find(query).first();

		return Optional.ofNullable(doc).map(o -> {
			LOGGER.debug("The Document is {}", doc);
			this.encryptionKeyUUID = (UUID) o.get("_id");
			this.encryptionKeyBase64 = Base64.getEncoder()
					.encodeToString(new BsonBinary((UUID) o.get("_id")).getData());
			return true;
		}).orElse(false);
	}

	private Map<String, Map<String, Object>> getKMSMap() {
		Map<String, Object> keyMap = getRemoteKMSDetails();
		Map<String, Map<String, Object>> providers = new HashMap<String, Map<String, Object>>();
		providers.put(REMOTE_KMS_PROVIDER, keyMap);
		return providers;
	}

	private Map<String, Object> getRemoteKMSDetails() {
		String masterKeyRegion = new String(KMS_AWS_REGION);
		String awsAccessKeyId = new String(KMS_AWS_ACCESS_KEY);
		String awsSecretAccessKey = new String(KMS_AWS_SECRET_ACCESS_KEY);
		Map<String, Object> providerDetails = new HashMap<String, Object>();
		providerDetails.put("accessKeyId", awsAccessKeyId);
		providerDetails.put("secretAccessKey", awsSecretAccessKey);
		providerDetails.put("region", masterKeyRegion);
		return providerDetails;
	}
	
	public void deleteKeyVaulCollection() {
		MongoClient mongoClient = MongoClients.create(DB_CONNECTION);
		MongoCollection<Document> collection = mongoClient.getDatabase(KEY_VAULT_DATABASE)
				.getCollection(KEY_VAULT_COLLECTION);
		collection.drop();
	}
}
