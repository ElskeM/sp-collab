package rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dao.CustomerNotFoundException;
import domain.Customer;
import service.OlfService;
import service.ServiceUnavailableException;

@Stateless
@Path("/customers")
public class CustomerResource {

	@Inject
	private OlfService service;

	@GET
	@Produces({ "application/JSON", "application/XML" })
	public Response getAllCustomers() {
		return Response.ok(service.getAllCustomer()).build();

	}

	// saknar implementation i service och db-klasserna, något vi ska ha?
	@GET
	@Produces({ "application/JSON", "application/XML" })
	@Path("{customerId}")
	public Response findCustomerById(@PathParam("customerId") int id) {
		try {
			return Response.ok(service.getCustomerById(id)).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

	@POST
	@Produces({ "application/JSON", "application/XML" })
	@Consumes({ "application/JSON", "application/XML" })
	public Response registerCustomer(Customer customer) {
		// try catch
		try {
			service.register(customer);
			return Response.ok().build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}

	}

}
