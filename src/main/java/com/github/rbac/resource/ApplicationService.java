package com.github.rbac.resource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/")
public class ApplicationService extends Application {
	
	public ApplicationService() {
        BeanConfig config = new BeanConfig();
        config.setTitle("Role Based Access Control API");
        config.setDescription("A sample api of Restful specs");
        config.setContact("http://github.com/FilipeSoares");
        config.setVersion("v1");
        config.setSchemes(new String[]{"http"});
        config.setHost("localhost:8080");
        config.setBasePath("/rbac-api/v1");
        config.setResourcePackage("com.github.rbac.resource");
        config.setScan(true);
    }
	
}
