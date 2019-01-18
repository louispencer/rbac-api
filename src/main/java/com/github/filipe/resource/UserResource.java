package com.github.filipe.resource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.github.filipe.dao.UserDAO;
import com.github.filipe.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	@Inject
	UserDAO dao;
	
	@Context 
	UriInfo uriInfo;
	
	@GET
	public Response list() {
		return Response.ok(dao.list("user.findAll")).build();
	}
	
	@POST
	@Transactional
	public Response create(User user) {
		
		ResponseBuilder rb = new ResponseBuilderImpl();
		rb.header("Location", uriInfo.getAbsolutePathBuilder().path( String.valueOf(dao.save(user).getId()) ));
		return rb.build();
		
	}

}
