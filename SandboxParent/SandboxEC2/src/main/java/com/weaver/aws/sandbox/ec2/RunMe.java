package com.weaver.aws.sandbox.ec2;

public class RunMe {

	public static void main(String[] args) {
		EC2 ec2 = new EC2();
		//ec2.CreateInstance();
		ec2.describeEC2Instances();
	}
}
