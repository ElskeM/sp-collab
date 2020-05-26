package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
import domain.Article;
import service.OlfService;

/**
 * @author Pontus
 *
 */
@Stateless
@Path("/articles")
public class ArticleResource {

	@Inject
	private OlfService service;

	@Context
	private UriInfo uriInfo;

	/**
	 * @param firstId
	 * @param secondId
	 * @return Response
	 */
	@GET
	@Produces({ "application/JSON" })
	public Response getAllArticlesBetweenId(@DefaultValue("0") @QueryParam("firstId") Integer firstId,
			@QueryParam("secondId") Integer secondId) {
		GenericEntity<List<Article>> articles = null;
		if (firstId == 0 && secondId == null) {
			articles = new GenericEntity<List<Article>>(service.getAllArticle()) {
			};
		}
		if (firstId != null && secondId != null) {
			try {
				articles = new GenericEntity<List<Article>>(service.getArticlesBetweenId(firstId, secondId)) {
				};
			} catch (ArticleNotFoundException e) {
				return Response.status(404).build();
			}

		}

		return Response.ok(articles).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * @param artNr
	 * @return Response
	 */
	@GET
	@Produces({ "application/JSON" })
	@Path("{artNr}")
	public Response getArticleById(@PathParam("artNr") int artNr) {
		try {
			Article art = service.getArticleById(artNr);
			Link selfLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("get").build();
			Link updateLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("update").type("put").build();
			Link deleteLink = Link.fromUri(uriInfo.getAbsolutePath()).rel("delete").type("delete").build();
			return Response.ok(art).links(selfLink, updateLink, deleteLink).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();

		}
	}

	/**
	 * @param artNr
	 * @return Response
	 */
	@DELETE
	@Path("{artNr}")
	public Response deleteArticle(@PathParam("artNr") int artNr) {
		try {
			service.deleteArticle(artNr);
			return Response.status(204).build();

		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		}

	}

	/**
	 * @param article
	 * @return Response
	 */
	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response registerArticle(Article article) {
		service.register(article);
		URI uri = null;
	
		try {
			uri = new URI(uriInfo.getAbsolutePath() + "/" + article.getArtNr());
		} catch (URISyntaxException e) {}

		return Response.created(uri).build();
	}

	/**
	 * @param artNr
	 * @param a
	 * @return Response
	 */
	@PUT
	@Path("{artNr}")
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response updateArticle(@PathParam("artNr") int artNr, Article a) {
		try {
			service.updateArticle(artNr, a.getDescription(), a.getPrice(), a.getStock());
			return Response.ok(service.getArticleById(artNr)).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		}

	}

}
