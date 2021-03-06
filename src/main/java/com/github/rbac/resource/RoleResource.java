package com.github.rbac.resource;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
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

import com.github.rbac.dao.RoleDAO;
import com.github.rbac.model.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Api(value="Roles Resource")
public class RoleResource {
		
	@Inject
	RoleDAO dao;
	
	@Context 
	UriInfo info;
	
	@GET
	@ApiOperation(httpMethod="GET", response=Role.class, value = "List all roles", responseContainer="List")
	public Response list() {
		return Response.ok(dao.list()).build();
	}
	
	@GET
	@Path("/{id}")
	@ApiOperation(httpMethod="GET", response=Role.class, value = "Get a unique role representation")
	@ApiResponse(code = 404, message = "Role not found")
	public Response get(@ApiParam(value="Identify of user", required=true) @PathParam("id") Long id) {
		return Response.ok(dao.find(id)).build();
	}
	
	@POST
	@Transactional
	@ApiOperation(httpMethod="POST", value = "Create a new role")
	@ApiResponses({@ApiResponse(code = 409, message = "Role already exists")})
	public Response create(Role role) {
		
		ResponseBuilder rb = new ResponseBuilderImpl();
		
		try {
			UriBuilder uriBuilder = info.getAbsolutePathBuilder();
			role.setActive(true);
			Long id = dao.create(role);		
			uriBuilder.path(String.valueOf(id));
			rb.header("Location", uriBuilder.build() );
			
			return rb.status(Status.CREATED).build();
		} catch (NonUniqueResultException e) {
			return rb.status(Status.CONFLICT).build();
		}
		
	}
	
	@PUT
	@Path("/{id}")
	@Transactional
	@ApiOperation(httpMethod="PUT", value = "Update a user")
	@ApiResponses({@ApiResponse(code = 409, message = "Role already exists"), @ApiResponse(code = 404, message = "Role not found")})
	public Response update(@PathParam("id") Long id, Role role) {
		role.setId(id);
		dao.update(role);
		return Response.ok(dao.find(id)).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Transactional
	@ApiOperation(httpMethod="DELETE", value = "Remove a role")
	@ApiResponses({@ApiResponse(code = 404, message = "Role not found")})
	public Response remove(@PathParam("id") Long id) {
		ResponseBuilder rb = new ResponseBuilderImpl();
		rb.status(Status.NO_CONTENT);
		dao.remove(id);
		return rb.build();
	}

}
