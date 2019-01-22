package com.github.filipe.resource;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.github.filipe.dao.UserDAO;
import com.github.filipe.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	@Inject
	UserDAO dao;
	
	@Context 
	UriInfo info;
	
	@GET
	public Response list() {
		return Response.ok(dao.listWithCriteria(Arrays.asList("name","email","registeredIn","active"))).build();
	}
	
	@POST
	@Transactional
	public Response create(User user) {
		
		ResponseBuilder rb = new ResponseBuilderImpl();
		UriBuilder uriBuilder = info.getAbsolutePathBuilder();
		user.setRegisteredIn(new Date());
		user.setActive(true);
		Long id = dao.save(user).getId();		
		uriBuilder.path(String.valueOf(id));
		rb.header("Location", uriBuilder.build() );
		
		return rb.status(Status.CREATED).build();
		
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, User user) {
		user.setId(id);
		return Response.ok(dao.save(user)).build();
	}

}
