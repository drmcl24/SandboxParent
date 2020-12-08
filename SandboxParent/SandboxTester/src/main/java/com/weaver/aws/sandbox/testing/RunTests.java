package com.weaver.aws.sandbox.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.weaver.aws.sandbox.ec2.EC2;


public class RunTests {

	private static final Logger log = LoggerFactory.getLogger(RunTests.class);
	
	public static void main(String[] args) {
		log.info("Start...");
		//Ec2Tests();
		RDS_Tests();
		log.info("Done..");
	}
	
	private static void RDS_Tests() {
		log.info("Starting RDS testing ");
		
		//MyRDS rds = new 
		//rds.describeInstances() ;
		//rds.CloseEc2Client();		
	}


}
