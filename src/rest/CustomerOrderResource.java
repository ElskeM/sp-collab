package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import domain.Article;
import domain.CustomerOrder;
import service.OlfServiceImpl;

@Stateless
@Path("/orders")
public class CustomerOrderResource {
	
	@Inject
	private OlfServiceImpl service;
	
	@GET
	@Produces({"application/JSON", "application/XML"})
	public List<CustomerOrder> getAllOrders() {
		return service.findAllOrders();
	}
	
	
	@POST
	@Produces({"application/JSON", "application/XML"})
	@Consumes({"application/JSON", "application/XML"})
	public CustomerOrder registerOrder(CustomerOrder order) {
		service.register(order);
		return order;
	}
	
	
	

}
