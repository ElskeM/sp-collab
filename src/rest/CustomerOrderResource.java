package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import domain.CustomerOrder;
import service.OlfService;

@Stateless
@Path("/orders")
public class CustomerOrderResource {
	
	@Inject
	private OlfService service;
	
	@GET
	@Produces({"application/JSON", "application/XML"})
	public List<CustomerOrder> getAllOrders() {
		return service.getAllOrders();
	}
	
	@POST
	@Produces({"application/JSON", "application/XML"})
	@Consumes({"application/JSON", "application/XML"})
	public CustomerOrder registerOrder(CustomerOrder order) {
		try {
			service.register(order);
		} catch (ArticleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}

}
