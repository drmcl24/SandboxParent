package com.weaver.aws.sandbox.ec2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RunMe {
	
	private static final Logger log = LoggerFactory.getLogger(RunMe.class);

	public static void main(String[] args) {
		log.info("Start...");
		//Ec2Tests();
		log.info("Done..");
	}
	
	private static void Ec2Tests() {
		log.info("Starting Ec2 testing ");
		EC2 ec2 = new EC2();
		//ec2.CreateInstance();
		//ec2.terminateEC2();
		ec2.describeEC2Instances();
		//always call close
		ec2.CloseEc2Client();
	}
	
}
