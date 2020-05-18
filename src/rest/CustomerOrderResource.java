package rest;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import domain.CustomerOrder;
import service.OlfService;
import service.ServiceUnavailableException;

@Stateless
@Path("/orders")
public class CustomerOrderResource {

	@Inject
	private OlfService service;

	@GET
	@Produces({ "application/JSON", "application/XML" })
	public List<CustomerOrder> getAllOrders() {
		return service.getAllOrders();
	}

	@POST
	@Produces({ "application/JSON", "application/XML" })
	@Consumes({ "application/JSON", "application/XML" })
	public Response registerOrder(CustomerOrder order) {
		try {
			service.register(order);
			URI uri = null;
			try {
				uri = new URI("/orders/" + order.getOrderNr());
			} catch (Exception e) {

			}
			return Response.created(uri).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}
	}

}
