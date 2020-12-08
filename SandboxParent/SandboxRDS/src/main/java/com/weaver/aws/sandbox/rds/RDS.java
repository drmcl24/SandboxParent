package com.weaver.aws.sandbox.rds;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.*;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.RdsException;

public class RDS {

	private final RdsClient rdsClient;
	private static final Logger log = LoggerFactory.getLogger(RDS.class);
	
	public RDS() {
	    Region region = Region.EU_WEST_1;
	    rdsClient = RdsClient.builder()
	    		.credentialsProvider(ProfileCredentialsProvider.builder()
                        .profileName("sandbox") //sandbox
                        .build())
	            .region(region)
	            .build();
	    log.info("Built RDS client OK");
	}
	
    public void describeInstances() {

        try {

            DescribeDbInstancesResponse response = rdsClient.describeDBInstances();

            List<DBInstance> instanceList = response.dbInstances();

            for (DBInstance instance: instanceList) {
            	log.info("Instance arn is: {}", instance.dbInstanceArn());
                log.info("Instance Identifier is: {}", instance.dbInstanceIdentifier());
                log.info("The Engine is {}" , instance.engine());
                log.info("Connection endpoint is {}" , instance.endpoint().address());
                log.info("Instance status is: {}",instance.dbInstanceStatus());
            }

        } catch (RdsException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        // snippet-end:[rds.java2.describe_instances.main]
    }
	
    public void closeRDS() {
    try {
        rdsClient.close();
		log.info("RDS client was closed OK");
	} catch (Exception e) {
		log.error("Problem closing RDS client. Error given as {}",e.getMessage());
	}
}

}
