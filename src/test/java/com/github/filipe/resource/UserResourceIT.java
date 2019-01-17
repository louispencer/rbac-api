package com.github.filipe.resource;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.api.Property;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gson.JsonObject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class UserResourceIT {
	
	@ArquillianResource
	private URL url;
	
	private RequestSpecification requestSpecification;
	
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
	
	@Before
	public void load() throws URISyntaxException {
		
		final RequestSpecBuilder request = new RequestSpecBuilder();
        request.setBaseUri(url.toURI())
        		.setBasePath("users")
        		.setAccept(MediaType.APPLICATION_JSON);
        
        this.requestSpecification = request.build();
		
	}
	
	public void listTest() {
		
		given(requestSpecification).when().get()
			.then()
			.assertThat().statusCode(anyOf(is(Response.Status.OK.getStatusCode()), is(Response.Status.PARTIAL_CONTENT.getStatusCode())));
		
	}
	
	public void createTest() {
		
		String nome = "User" + System.currentTimeMillis();

		JsonObject user = new JsonObject();
		user.addProperty("name", nome);
		
		given(requestSpecification)
			.contentType(ContentType.JSON)
			.body(user.toString())
			.when().post().then()
			.assertThat().header("Location", notNullValue())
			.statusCode(is(Response.Status.CREATED.getStatusCode()));
		
	}

}
