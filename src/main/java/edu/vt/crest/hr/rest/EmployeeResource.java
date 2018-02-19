package edu.vt.crest.hr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.vt.crest.hr.entity.EmployeeEntity;
import edu.vt.crest.hr.services.EmployeeService;

/**
 * Serves as a RESTful endpoint for manipulating EmployeeEntity(s)
 */
@Stateless
@Path("/employees")
public class EmployeeResource {

	//Used to interact with EmployeeEntity(s)
	@Inject
	EmployeeService employeeService;

	/**
	 * TODO - Implement this method
	 * @param employee the EmployeeEntity to create
	 * @return a Response containing the new EmployeeEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Valid final EmployeeEntity employee) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(employeeService.createEmployee(employee))
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
	}

	/**
	 * TODO - Implement this method
	 * @param id of the EmployeeEntity to return
	 * @return a Response containing the matching EmployeeEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") final Long id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(employeeService.findById(id))
                    .build();
        } catch (NoResultException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
	}

	/**
	 * TODO - Implement this method
	 * @param startPosition the index of the first EmployeeEntity to return
	 * @param maxResult the maximum number of EmployeeEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of EmployeeEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
        return employeeService.listAll(startPosition, maxResult);
	}

	/**
	 * TODO - Implement this method
	 * @param id the id of the EmployeeEntity to update
	 * @param employee the entity used to update
	 * @return a Response containing the updated EmployeeEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") final Long id, @Valid final EmployeeEntity employee) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(employeeService.update(id, employee))
                    .build();
        } catch (NoResultException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
	}
}
