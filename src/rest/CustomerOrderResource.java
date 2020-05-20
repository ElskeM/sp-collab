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
import javax.ws.rs.core.Response;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.OrderNotFoundException;
import domain.CustomerOrder;
import service.OlfService;
import service.ServiceUnavailableException;

@Stateless
@Path("/orders")
public class CustomerOrderResource {

	@Inject
	private OlfService service;

	@GET
	@Produces({ "application/JSON" })
	public Response getAllOrders() {
		try {
			List<CustomerOrder> allOrders = service.getAllOrders();
			return Response.ok(allOrders).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}
	}

	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
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

	@GET
	@Produces({"application/JSON"})
	@Path("{orderNr}")
	public Response OrderById(@PathParam("orderNr") int orderNr) {
		try {
			CustomerOrder result = service.getOrderById(orderNr);
			return Response.ok(result).build();
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		}
	}
	
	
	@PUT
	@Path("{orderNr}")
	@Produces({"application/JSON"})
	@Consumes({"application/JSON"})
	public Response updateCustomerOrder(@PathParam("orderNr") int orderNr, CustomerOrder cO) {
		try {
			service.updateCustomerOrder(orderNr, cO.getArticles(), cO.getDispatchDate());
			return Response.ok(service.getOrderById(orderNr)).build();
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		}
	}
	
	@DELETE
	@Path("{orderNr}")
	public Response deleteCustomerOrder(@PathParam("orderNr") int orderNr) {
		try {
			service.deleteCustomerOrder(orderNr);
			return Response.status(204).build();
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		}
		
	}
	
}
