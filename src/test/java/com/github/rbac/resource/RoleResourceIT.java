package com.github.rbac.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.rbac.model.Role;
import com.google.gson.GsonBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class RoleResourceIT {
	
	@ArquillianResource
	private URL url;
	private RequestSpecification requestSpecification;
	
	private static final String DESCRIPTION = "Test Profile";
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
		        .addPackages(true, "com.github.rbac")
		        .addAsResource("META-INF/persistence.xml")
		        .addAsLibraries(archives)
		        .addAsWebInfResource( new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml")
		        .addAsWebInfResource("jboss-web.xml");
		
		return war;
	}
	
	@Before
	public void setup() throws URISyntaxException {
		
		final RequestSpecBuilder request = new RequestSpecBuilder();
        request.setBaseUri(url.toURI())
        		.setBasePath("roles")
        		.setAccept(MediaType.APPLICATION_JSON);
        
        this.requestSpecification = request.build();
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void create() {
		given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(loadBody())
				.when().log().all().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2)
	public void update() {
		
		String location = given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header("Location");
		
		String id = location.split("roles/")[1];
		
		given(requestSpecification)
			.pathParam("id", id)
			.contentType(ContentType.JSON)
			.body(loadBody())
			.when().put("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.OK.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3)
	public void list() {
		
		given(requestSpecification)
			.when()
				.get()
			.then()
				.assertThat().statusCode(anyOf(is(Response.Status.OK.getStatusCode()), is(Response.Status.PARTIAL_CONTENT.getStatusCode())));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4)
	public void get() {
		
		String location = given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header("Location");
		
		String id = location.split("roles/")[1];
		
		given(requestSpecification)
			.pathParam("id", id)
			.contentType(ContentType.JSON)
			.when().get("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.OK.getStatusCode()))
				.assertThat().body(notNullValue());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5)
	public void remove() {
		
		String location = given(requestSpecification)
				.contentType(ContentType.JSON)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header("Location", notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header("Location");
		
		String id = location.split("roles/")[1];
		
		given(requestSpecification)
			.pathParam("id", id)
			.when().delete("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.NO_CONTENT.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6)
	public void notFound() {
		
		String id = String.valueOf(System.currentTimeMillis());
		
		given(requestSpecification)
			.pathParam("id", id)
			.contentType(ContentType.JSON)
			.when().get("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.NOT_FOUND.getStatusCode()))
				.assertThat().body(notNullValue());
		
	}


	private static String loadBody() {
		return new GsonBuilder().create().toJson(new Role(DESCRIPTION, ACTIVE));
	}


}
