package rest;

import java.net.URI;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import dao.CustomerNotFoundException;
import domain.Customer;
import domain.CustomerOrder;
import service.OlfService;
import service.ServiceUnavailableException;

@Stateless
@Path("/customers")
public class CustomerResource {

	@Inject
	private OlfService service;

	@GET
	@Produces({ "application/JSON"})
	public Response getAllCustomers() {
		try {
			GenericEntity<List<Customer>> allCustomers = 
					new GenericEntity<List<Customer>>(service.getAllCustomer()) {};
			return Response.ok(allCustomers).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}

	}

	@GET
	@Produces({ "application/JSON" })
	@Path("{customerId}")
	public Response findCustomerById(@PathParam("customerId") int id) {
		try {
			return Response.ok(service.getCustomerById(id)).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response registerCustomer(Customer customer) {
		try {

			service.register(customer);
			URI uri = null;
			try {
				uri = new URI("customers/" + customer.getCnr());
			} catch (Exception e) {
			}
			return Response.created(uri).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}

	}

	@PUT
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

	@DELETE
	@Path("{customerId}")
	public Response deleteCustomer(@PathParam("customerId") int id) {//funkar inte om den finns i en order?
		try {
			service.deleteCustomer(id);
			return Response.status(204).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
	}

}
