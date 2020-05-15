package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import domain.Customer;
import service.OlfServiceImpl;

@Stateless
@Path("/customers")
public class CustomerResource {
	
	@Inject
	private OlfServiceImpl service;

	
	@GET
	@Produces({"application/JSON", "application/XML"})
	public List<Customer> getAllCustomers(){
		return service.findAllCustomer();
		
	}
	
	
	//saknar implementation i service och db-klasserna, något vi ska ha?
	@GET
	@Produces({"application/JSON", "application/XML"})
	@Path("{customerId}")
	public Customer findCustomerById(@PathParam("customerId") int id) {
		return null;
	}
	
	@POST
	@Produces({"application/JSON", "application/XML"})
	@Consumes({"application/JSON", "application/XML"})
	public Customer registerCustomer(Customer customer) {
		service.register(customer);
		return customer;
		
	}

}
