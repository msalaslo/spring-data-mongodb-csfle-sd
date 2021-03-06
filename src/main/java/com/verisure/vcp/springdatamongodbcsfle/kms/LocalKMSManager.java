package com.verisure.vcp.springdatamongodbcsfle.kms;

import java.io.FileInputStream;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.BsonBinary;
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
public class LocalKMSManager {

	@Value(value = "${spring.data.mongodb.uri}")
	private String DB_CONNECTION;
	@Value(value = "${spring.data.mongodb.key.vault.database}")
	private String KEY_VAULT_DATABASE;
	@Value(value = "${spring.data.mongodb.key.vault.collection}")
	private String KEY_VAULT_COLLECTION;
	@Value(value = "${spring.data.mongodb.kmsprovider}")
	private String KMS_PROVIDER;
	@Value(value = "${spring.data.mongodb.key.vault.name}")
	private String KEY_NAME;
	@Value(value = "${spring.data.mongodb.encryption.masterKeyPath}")
	private String MASTER_KEY_PATH;

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
		DataKeyOptions dataKeyOptions = new DataKeyOptions();
		dataKeyOptions.keyAltNames(Arrays.asList(KEY_NAME));
		BsonBinary dataKeyId = getClientEncryption().createDataKey(KMS_PROVIDER, dataKeyOptions);

		this.encryptionKeyUUID = dataKeyId.asUuid();
		LOGGER.debug("DataKeyID [UUID]{}", dataKeyId.asUuid());

		String base64DataKeyId = Base64.getEncoder().encodeToString(dataKeyId.getData());
		this.encryptionKeyBase64 = base64DataKeyId;
		LOGGER.debug("DataKeyID [base64]: {}", base64DataKeyId);
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

	private byte[] getMasterKey() {

		byte[] localMasterKey = new byte[96];

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(MASTER_KEY_PATH);
			fis.read(localMasterKey, 0, 96);
		} catch (Exception e) {
			LOGGER.error("Error Initializing the master key");
		}
		return localMasterKey;
	}

	private Map<String, Map<String, Object>> getKMSMap() {
		Map<String, Object> keyMap = Stream.of(new AbstractMap.SimpleEntry<>("key", getMasterKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return Stream.of(new AbstractMap.SimpleEntry<>(KMS_PROVIDER, keyMap))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public ClientEncryption getClientEncryption() {

		String keyVaultNamespace = KEY_VAULT_DATABASE + "." + KEY_VAULT_COLLECTION;
		ClientEncryptionSettings clientEncryptionSettings = ClientEncryptionSettings.builder()
				.keyVaultMongoClientSettings(MongoClientSettings.builder()
						.applyConnectionString(new ConnectionString(DB_CONNECTION)).build())
				.keyVaultNamespace(keyVaultNamespace).kmsProviders(this.getKMSMap()).build();

		ClientEncryption clientEncryption = ClientEncryptions.create(clientEncryptionSettings);

		return clientEncryption;
	}

	public void deleteKeyVaulCollection() {

		MongoClient mongoClient = MongoClients.create(DB_CONNECTION);
		MongoCollection<Document> collection = mongoClient.getDatabase(KEY_VAULT_DATABASE)
				.getCollection(KEY_VAULT_COLLECTION);
		collection.drop();

	}
}
