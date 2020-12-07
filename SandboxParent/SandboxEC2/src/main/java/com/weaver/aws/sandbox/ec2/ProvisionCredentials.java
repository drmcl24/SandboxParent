package com.weaver.aws.sandbox.ec2;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

public interface ProvisionCredentials {
	
	default ProfileCredentialsProvider getProfileCredentials() {
		return ProfileCredentialsProvider.builder().profileName("sandbox").build();
	}
	
	ProfileCredentialsProvider getProfileCredentials(String profileName);

}
