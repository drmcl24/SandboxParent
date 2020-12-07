package com.weaver.aws.sandbox.secrets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.DescribeSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.DescribeSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

public class SecretManager {
	
	private final SecretsManagerClient secretsClient;
	private final Logger logger = LoggerFactory.getLogger(SecretManager.class);
	 
    public SecretManager() {
		super();
		Region region = Region.EU_WEST_1;
	    secretsClient = SecretsManagerClient.builder()
	    		.credentialsProvider(ProfileCredentialsProvider.builder()
                        .profileName("sandbox") //sandbox
                        .build())
	            .region(region)
	            .build();
	}

    public void DescribeSecret(String secretName) throws SecretsManagerException {
            DescribeSecretRequest secretRequest = DescribeSecretRequest.builder()
                .secretId(secretName)
                .build();
            DescribeSecretResponse secretResponse = secretsClient.describeSecret(secretRequest);
            System.out.println("here is some info on a secret: "+ secretResponse.toString() );
    }

    public void ShowSecret(String secretName) throws SecretsManagerException{
        GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            System.out.println("Here is the secret associated with secret name '" + secretName +"'  :- " + valueResponse.secretString());
            System.out.println("name  " + valueResponse.name());

    }

}
