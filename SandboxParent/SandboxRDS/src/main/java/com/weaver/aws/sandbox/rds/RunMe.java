package com.weaver.aws.sandbox.rds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RunMe {
	
	private static final Logger log = LoggerFactory.getLogger(RunMe.class);

	public static void main(String[] args) {
		log.info("Start...");
		RDS_Tests();
		log.info("Done..");

	}
	private static void RDS_Tests() {
		log.info("Starting RDS testing ");		
		RDS rds = new RDS();
		rds.describeInstances() ;
		//rds.closeRDS();	
	}
}
