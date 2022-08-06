package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//JPA Auditing 기능 활성화하여 CreateTime, ModifiedTime를 관리하도록 위임 
@EnableJpaAuditing
public class DxChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DxChallengeApplication.class, args);
	}

}
