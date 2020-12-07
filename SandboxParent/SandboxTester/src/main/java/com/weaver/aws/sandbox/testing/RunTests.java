package com.weaver.aws.sandbox.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.weaver.aws.sandbox.ec2.EC2;

public class RunTests {

	private static final Logger log = LoggerFactory.getLogger(RunTests.class);
	
	public static void main(String[] args) {
		log.info("Start...");
		log.info("Calling EC2 class");
		EC2 ec2 = new EC2();
		ec2.CreateInstance();
		log.info("Done..");
	}
}
