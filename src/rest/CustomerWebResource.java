package rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import dao.DataAccessException;
import domain.Customer;
import service.OlfService;
import service.ServiceUnavailableException;

@Stateless
@Path("/customersweb")
public class CustomerWebResource {

	@Inject
	private OlfService service;

	@Context
	private UriInfo uriInfo;

	/**
	 * @Depricated
	 */
//	@POST
//	@Produces({ "application/JSON" })
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response registerCustomer(@FormParam("firstname") String firstName, 
//									 @FormParam("lastname") String lastName,
//									 @FormParam("address") String address,
//									 @FormParam("zipcode") String zipCode,
//									 @FormParam("city") String city
//									 ) {
//		
//		Customer customer = new Customer(firstName, lastName, address, zipCode, city, 0);
//		
//		try {
//			service.register(customer);
//		} catch (ServiceUnavailableException e1) {
//			return Response.status(500).build();
//		}
//		URI uri = null;
//			try {
//				uri = new URI(uriInfo.getAbsolutePath() + "/" + customer.getCustomerNr());
//			} catch (URISyntaxException e) {}
//	
//		return Response.created(uri).build();
//
//	}

	/**
	 * @param customer
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerCustomer(@Valid Customer customer) {

		try {
			service.register(customer);
		} catch (ServiceUnavailableException e1) {
			return Response.status(500).build();
		} catch (DataAccessException e) {
			return Response.serverError().entity(new ErrorMessage(e.getMessage(), MESSAGE_TYPE.ServerError)).build();
		}
		URI uri = null;

		try {
			uri = new URI(uriInfo.getAbsolutePath() + "/" + customer.getCustomerNr());
		} catch (URISyntaxException e) {
		}


		return Response.created(uri).build();
	}
	
	

}
