package com.define.commons;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringcloudActivitiApplicationTests {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IdentityService identityService;

	@Test
	public void contextLoads() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("林");
		userEntity.setLastName("银城");
		userEntity.setEmail("c1053595207@163.com");
		userEntity.setId("1");
		identityService.saveUser(userEntity);
	}

	@Test
	public void workflowDeploy() {
		Deployment deploy = repositoryService.createDeployment()
		.addClasspathResource("processes/empty.bpm")
		.deploy();
	}
}
