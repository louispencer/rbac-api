package com.github.filipe.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.api.Property;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.filipe.model.User;
import com.google.gson.GsonBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class UserResourceIT {
	
	@ArquillianResource
	private URL url;
	private RequestSpecification requestSpecification;
	
	private static final String NAME = "Test User";
	private static final String EMAIL = "user@test.com";
	private static final String PASSWORD = "123456";
	private static final LocalDate REGISTERED = LocalDate.now();
	private static final Boolean ACTIVE = true;
	
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
		        .addAsWebInfResource( new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml")
		        .addAsWebInfResource("jboss-web.xml");
		
		return war;
	}
	
	@Before
	public void load() throws URISyntaxException {
		
		final RequestSpecBuilder request = new RequestSpecBuilder();
        request.setBaseUri(url.toURI())
        		.setBasePath("users")
        		.setAccept(MediaType.APPLICATION_JSON);
        
        this.requestSpecification = request.build();
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2)
	public void list() {
		
		given(requestSpecification)
			.when()
				.get()
			.then()
				.assertThat().statusCode(anyOf(is(Response.Status.OK.getStatusCode()), is(Response.Status.PARTIAL_CONTENT.getStatusCode())));
		
	}
	
	@Test
	@Ignore
	@RunAsClient
	@InSequence(1)
	public void create() {
		
		String location = given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(new GsonBuilder().create().toJson(new User(NAME, EMAIL, PASSWORD, REGISTERED, ACTIVE, null)))
				.when().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header("Location");
		
		System.out.println(location);
		
	}
	
	@Ignore
	public void update() {
		
		String location = given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(new GsonBuilder().create().toJson(new User(NAME, EMAIL, PASSWORD, REGISTERED, ACTIVE, null)))
				.when().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header("Location");
		
		System.out.println(location);
		
	}

}
