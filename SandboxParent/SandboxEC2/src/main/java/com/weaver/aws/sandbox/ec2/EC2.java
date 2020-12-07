package com.weaver.aws.sandbox.ec2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;

public class EC2 implements ProvisionCredentials {
	
	private static final Logger log = LoggerFactory.getLogger(EC2.class);

	@Override
	public ProfileCredentialsProvider getProfileCredentials(String profileName) {
		return ProfileCredentialsProvider.builder().profileName(profileName).build();
	}

	
	public void CreateInstance() {
	    String name = "AnotherDaveInstance";
	    String amiId = "ami-0bb3fad3c0286ebd5";
	
	    Region region = Region.EU_WEST_1;
	    Ec2Client ec2 = Ec2Client.builder()
	    		.credentialsProvider(this.getProfileCredentials())
	            .region(region)
	            .build();
	
	    String instanceId = createEC2Instance(ec2,name, amiId) ;
	    log.info("The Amazon EC2 Instance ID created is "+instanceId);
	    ec2.close();
    
	}
	
    // snippet-start:[ec2.java2.describe_instances.main]
    public void describeEC2Instances(){

        boolean done = false;
        String nextToken = null;
        
	    Region region = Region.EU_WEST_1;
	    Ec2Client ec2 = Ec2Client.builder()
	    		.credentialsProvider(this.getProfileCredentials())
	            .region(region)
	            .build();
	

        try {

            do {
                DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(6).nextToken(nextToken).build();
                DescribeInstancesResponse response = ec2.describeInstances(request);

                for (Reservation reservation : response.reservations()) {
                    for (Instance instance : reservation.instances()) {
                    log.info(
                            "Found Reservation with id {}, " +
                                    "AMI {}, " +
                                    "type {}, " +
                                    "state {} " +
                                    "and monitoring state {}",
                            instance.instanceId(),
                            instance.imageId(),
                            instance.instanceType(),
                            instance.state().name(),
                            instance.monitoring().state());
                    log.info("");
                }
            }
                nextToken = response.nextToken();
            } while (nextToken != null);

        } catch (Ec2Exception e) {
        	log.error("Describe Instances problem:- {} ", e.awsErrorDetails().errorMessage());
        }
    }


	private String createEC2Instance(Ec2Client ec2, String name, String amiId) {
        RunInstancesRequest runRequest = RunInstancesRequest.builder()
                .imageId(amiId)
                .instanceType(InstanceType.T1_MICRO)
                .maxCount(1)
                .minCount(1)
                .build();

        RunInstancesResponse response = ec2.runInstances(runRequest);
        String instanceId = response.instances().get(0).instanceId();

        Tag tag = Tag.builder()
                .key("InstanceName")
                .value(name)
                .build();

        CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                .resources(instanceId)
                .tags(tag)
                .build();

        try {
            ec2.createTags(tagRequest);
            log.info("Successfully started EC2 Instance {} based on AMI {}", instanceId, amiId);

          return instanceId;
        } catch (Ec2Exception e) {
        	log.error(e.awsErrorDetails().errorMessage());
        }
        // snippet-end:[ec2.java2.create_instance.main]
        return " A Problem..";
	}
}
