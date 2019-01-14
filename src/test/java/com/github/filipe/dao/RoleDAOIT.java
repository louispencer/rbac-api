package com.github.filipe.dao;

import java.io.File;
import java.util.HashSet;

import javax.inject.Inject;

import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.api.Property;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.filipe.model.Role;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class RoleDAOIT {
	
	@Deployment
	public static WebArchive deploy() {
		
		File[] archives = Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.TEST, ScopeType.PROVIDED, ScopeType.RUNTIME)
				.resolve()
				.withTransitivity()
				.asFile();
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "rbac-api.war")
		        .addPackages(true, "com.github.filipe")
		        .addAsResource("META-INF/persistence.xml")
		        .addAsLibraries(archives)
		        .addAsWebInfResource( new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml");
		
		return war;
	}
	
	@Inject
	RoleDAO dao;
	
	private static final String ATTR_DESCRIPTION = "Test User";
	private static final Boolean ATTR_ACTIVE = true;
	
	@Transactional
	@Test
	@InSequence(1)
	public void saveTest() {
		Assert.assertNotNull(dao.save(new Role(ATTR_DESCRIPTION, ATTR_ACTIVE, new HashSet<>())));
	}
	
	@Test
	@InSequence(2)
	public void listTest() {
		Assert.assertNotNull(dao.list());
	}
	
	@Test
	@InSequence(3)
	public void findTest() {
		
		Role role = dao.save(new Role(ATTR_DESCRIPTION, ATTR_ACTIVE, new HashSet<>()));
		Assert.assertNotNull(dao.find(role.getId()));
	}

}
