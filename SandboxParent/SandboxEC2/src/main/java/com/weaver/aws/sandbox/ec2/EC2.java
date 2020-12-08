package com.weaver.aws.sandbox.ec2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.InstanceStateChange;

public class EC2 implements ProvisionCredentials {
	
	private static final Logger log = LoggerFactory.getLogger(EC2.class);
	private final Ec2Client ec2;

	@Override
	public ProfileCredentialsProvider getProfileCredentials(String profileName) {
		return ProfileCredentialsProvider.builder().profileName(profileName).build();
	}
	
	public EC2() {
	    Region region = Region.EU_WEST_1;
	    ec2 = Ec2Client.builder()
	    		.credentialsProvider(this.getProfileCredentials())
	            .region(region)
	            .build();
	    log.info("Built EC2 client OK");
	    
	}

	
	public void CreateInstance() {
	    String name = "AnotherDaveInstance";
	    String amiId = "ami-0bb3fad3c0286ebd5";
	
	    String instanceId = createEC2Instance(name, amiId) ;
	    log.info("The Amazon EC2 Instance ID created is "+instanceId);
	    ec2.close();
    
	}


	private String createEC2Instance( String name, String amiId) {
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
            log.info("Successfully started EC2 Instance %s based on AMI %s", instanceId, amiId);

          return instanceId;
        } catch (Ec2Exception e) {
        	log.error(e.awsErrorDetails().errorMessage());
        }
        // snippet-end:[ec2.java2.create_instance.main]
        return " A Problem..";
	}
	
	public void describeEC2Instances(){
		log.info("Looking for Ec2 instances..");
        boolean done = false;
        String nextToken = null;

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
                    System.out.println("");
                }
            }
                nextToken = response.nextToken();
            } while (nextToken != null);

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
	
    public void terminateEC2() {
    	String instanceID="i-0dffaa674eca5f17f";

           try{
                TerminateInstancesRequest ti = TerminateInstancesRequest.builder()
                    .instanceIds(instanceID)
                    .build();

                TerminateInstancesResponse response = ec2.terminateInstances(ti);

                List<InstanceStateChange> list = response.terminatingInstances();

                for (int i = 0; i < list.size(); i++) {
                    InstanceStateChange sc = (list.get(i));
                    log.info("The ID of the terminated instance is {} ",sc.instanceId());
                }
            } catch (Ec2Exception e) {
                System.err.println(e.awsErrorDetails().errorMessage());
                System.exit(1);
            }
         }
	
	public void CloseEc2Client() {
		try {
			//ec2.close();
			log.info("ec2 client was closed OK");
		} catch (Exception e) {
			log.error("Problem closing EC2 client. Error given as {}",e.getMessage());
		}
	}
}
