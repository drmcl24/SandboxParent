package com.weaver.aws.sandbox.secrets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunMe {

	private static Logger logger = LoggerFactory.getLogger(RunMe.class);
	
	public static void main(String[] args) {
		logger.info("Start");
		SecretManager secretManager = new SecretManager();
		try {
			secretManager.DescribeSecret("DaveSecret");
			//secretManager.ShowSecret("dev/rdmclink");//DaveSecret
		} catch (Exception e) {
			logger.error("Oops " + e.getMessage());
		}
	}
}
