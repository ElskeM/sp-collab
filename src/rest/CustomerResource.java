package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.CustomerNotFoundException;
import dao.ForbiddenDeleteException;
import domain.Customer;
import service.OlfService;
import service.ServiceUnavailableException;

/**
 * @author Simon
 *
 */
@Stateless
@Path("/customers")
public class CustomerResource {

	@Inject
	private OlfService service;

	@Context
	private UriInfo uriInfo;

	/**
	 * Takes a string and returns all customers whos last name includes that string
	 * 
	 * @param name the last name of the customer(s)
	 * @return Response with List<Customer> or 404 if no customers matches the param
	 *         name
	 */
	@GET
	@Produces({ "application/JSON" })
	public Response getCustomersByName(@QueryParam("lastName") String name) {
		try {
			GenericEntity<List<Customer>> foundCustomers;
			if (name == null) {
				foundCustomers = new GenericEntity<List<Customer>>(service.getAllCustomer()) {
				};
			} else {

				foundCustomers = new GenericEntity<List<Customer>>(service.getCustomerByName(name)) {
				};
			}
			return Response.ok(foundCustomers).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

	/**
	 * Takes an int and returns the Customers whos customerNr matches the given
	 * parameter
	 * 
	 * @param id a customerNr
	 * @return Response with Customer and hateoas-info or if no Customer with given
	 *         id exists status 404
	 */
	@GET
	@Produces({ "application/JSON" })
	@Path("{customerId}")
	public Response getCustomerById(@PathParam("customerId") int id) {
		try {
			Customer result = service.getCustomerById(id);
			Link selfLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("get").build();
			Link updateLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("update").type("put").build();
			Link deleteLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("delete").type("delete").build();
			return Response.ok(result).links(selfLink, updateLink, deleteLink).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

	/**
	 * Takes a Customer and registers it into the database
	 * 
	 * @param customer
	 * @return Response with uriInfo about the inserted customer
	 */
	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response registerCustomer(Customer customer) {
		try {
			service.register(customer);
		} catch (ServiceUnavailableException e1) {
			return Response.status(500).build();
		}
		URI uri = null;
		try {
			uri = new URI(uriInfo.getAbsolutePath() + "/" + customer.getCustomerNr());
		} catch (URISyntaxException e) {
		}

		return Response.created(uri).build();
	}

	/**
	 * Takes an Integer and a Customer and updates an already existing customer with
	 * the given customerNr using the information in the Customer object
	 * 
	 * @param cnr
	 * @param customer
	 * @return Response with the updated Customer or status 404
	 */
	@PUT
	@Path("{customerId}")
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response updateCustomer(@QueryParam("id") Integer cnr, Customer customer) {
		try {
			service.updateCustomer(cnr, customer);
			return Response.ok(service.getCustomerById(cnr)).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

	/**
	 * Takes a customerNr and deletes the Customer with the given customerNr if the
	 * Customer has an order the deletion is forbidden
	 * 
	 * @param id
	 * @return Response with status 209, 404 or 403
	 */
	@DELETE
	@Path("{customerId}")
	public Response deleteCustomer(@PathParam("customerId") int id) {
		try {
			service.deleteCustomer(id);
			return Response.status(209).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		} catch (ForbiddenDeleteException e) {
			return Response.status(403).build();
		}
	}
}
