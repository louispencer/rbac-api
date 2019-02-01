package com.github.rbac.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.github.rbac.dao.UserDAO;
import com.github.rbac.model.User;

import io.swagger.annotations.Api;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Api
public class UserResource {
	
	@Inject
	UserDAO dao;
	
	@Context 
	UriInfo info;
	
	@GET
	public Response list() {
		return Response.ok(dao.list()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		
		return Response
				.ok(dao.find(id))
				.links(loadLinks(id, info).toArray(new Link[0]))
				.build();
		
	}
	
	@POST
	@Transactional
	public Response create(User user) {
		
		ResponseBuilder rb = new ResponseBuilderImpl();
		UriBuilder uriBuilder = info.getAbsolutePathBuilder();
		user.setRegisteredIn(new Date());
		user.setActive(true);
		Long id = dao.create(user);		
		uriBuilder.path(String.valueOf(id));
		rb.header("Location", uriBuilder.build() );
		
		return rb.status(Status.CREATED).build();
		
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, User user) {
		user.setId(id);
		dao.update(user);
		return Response.ok(dao.find(id)).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Transactional
	public Response remove(@PathParam("id") Long id) {
		ResponseBuilder rb = new ResponseBuilderImpl();
		rb.status(Status.NO_CONTENT);
		dao.remove(id);
		return rb.build();
	}
	
	private List<Link> loadLinks(Long id, UriInfo info) {
		
		UriBuilder uriBuilder = info.getBaseUriBuilder();
	    uriBuilder = uriBuilder.path(UserResource.class).path(String.valueOf(id));
	    
	    Link self = Link.fromUriBuilder(uriBuilder).rel("self").type(MediaType.APPLICATION_JSON).build();
	    Link profiles = Link.fromUriBuilder(uriBuilder.path(ProfileResource.class).queryParam("user", id)).rel("profiles").type(MediaType.APPLICATION_JSON).build();
	    
	    List<Link> links = new ArrayList<>();
		links.add(self);
		links.add(profiles);
		
		return links;
	}

}
