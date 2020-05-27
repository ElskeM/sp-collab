package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
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

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.OrderNotFoundException;
import dao.OutOfStockException;
import domain.CustomerOrder;
import service.OlfService;
import service.ServiceUnavailableException;

/**
 * @author elske
 *
 */
@Stateless
@Path("/orders")
public class CustomerOrderResource {

	@Inject
	private OlfService service;

	@Context
	private UriInfo uriInfo;

	private Response getAllOrders() {
		try {
			List<CustomerOrder> allOrders = service.getAllOrders();
			return Response.ok(allOrders).build();
		} catch (EJBTransactionRolledbackException e) {
			return Response.serverError()
					.entity(new ErrorMessage("Error communicating with the database backend", MESSAGE_TYPE.ServerError))
					.build();
		}
	}

	/**
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@GET
	@Produces({ "application/JSON" })
	public Response getOrdersBetweenDates(@QueryParam("fromDate") String fromDate,
			@QueryParam("toDate") String toDate) {

		if (fromDate == null && toDate == null) {
			return getAllOrders();
		}

		GenericEntity<List<CustomerOrder>> orders;
		try {
			orders = new GenericEntity<List<CustomerOrder>>(service.getOrdersBetweenDates(fromDate, toDate)) {
			};
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		}
		return Response.ok(orders).build();
	}

	/**
	 * @param order
	 * @return Response
	 */
	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response registerOrder(CustomerOrder order) {
		try {
			System.out.print("Registering new order");
			service.register(order);
			System.out.print("Registered new order as number " + order.getOrderNr());
			URI uri = null;
			try {
				uri = new URI("/orders/" + order.getOrderNr());
			} catch (URISyntaxException e) {

			}
			return Response.created(uri).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		} catch (OutOfStockException e1) {
			return Response.ok(new ErrorMessage(e1.getMessage(), MESSAGE_TYPE.ArticleOutOfStock)).build();
		}
	}

	/**
	 * @param orderNr
	 * @return Response
	 */
	@GET
	@Produces({ "application/JSON" })
	@Path("{orderNr}")
	public Response OrderById(@PathParam("orderNr") int orderNr) {
		try {
			CustomerOrder result = service.getOrderById(orderNr);
			Link selfLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("get").build();
			Link updateLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("update").type("put").build();
			Link deleteLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("delete").type("delete").build();
			return Response.ok(result).links(selfLink, updateLink, deleteLink).build();
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		}
	}

	/**
	 * @param orderNr
	 * @param cO
	 * @return Response
	 */
	@PUT
	@Path("{orderNr}")
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response updateCustomerOrder(@PathParam("orderNr") int orderNr, CustomerOrder cO) {
		try {
			service.updateCustomerOrder(orderNr, cO.getArticles(), cO.getDispatchDate());
			return Response.ok(service.getOrderById(orderNr)).build();
		} catch (OrderNotFoundException e) {
			return Response.status(404).build();
		} catch (OutOfStockException e) {
			return Response.status(404).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		}
	}

	/**
	 * @param orderNr
	 * @return Response
	 */
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
