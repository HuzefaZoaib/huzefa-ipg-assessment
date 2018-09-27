package com.ipg.assessment.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.ipg.assessment"})
public class IPGAssessmentApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(IPGAssessmentApplication.class, args);
	}
}
