package rest;

import java.net.URI;
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
import service.ServiceUnavailableException;

@Stateless
@Path("/articles")
public class ArticleResource {

	@Inject
	private OlfService service;

	@Context
	private UriInfo uriInfo;

//	@GET
//	@Produces({"application/JSON", "application/XML"})
//	public Response getAllArticles() {
//		return Response.ok(service.getAllArticle()).build();
//	}

	@GET
	@Produces({ "application/JSON" })
	public Response getAllArticlesBetweenId(@DefaultValue("0") @QueryParam("firstId") Integer firstId,
			@QueryParam("secondId") Integer secondId) {
		if (firstId == 0 && secondId == null) {
			GenericEntity<List<Article>> articles = new GenericEntity<List<Article>>(service.getAllArticle()) {
			};
			return Response.ok(articles).build();
		}
		if (firstId != null && secondId != null) {
			GenericEntity<List<Article>> articles = new GenericEntity<List<Article>>(
					service.getArticlesBetweenId(firstId, secondId)) {
			};
			return Response.ok(articles).build();
		}
		return Response.status(400).build();

	}

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

	@POST
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response registerArticle(Article article) {
		try {
			service.register(article);
			URI uri = null;
			try {
				uri = new URI(uriInfo.getAbsolutePath() + "/" + article.getArtNr());
			} catch (Exception e) {
			}
			return Response.created(uri).build();
		} catch (ServiceUnavailableException e) {
			return Response.status(504).build();
		}
	}
	
	@PUT
	@Path("{artNr}")
	@Produces({ "application/JSON" })
	@Consumes({ "application/JSON" })
	public Response updateArticle(@PathParam("employeeNo") int artNr, Article a) {
		try {
			service.updateArticle(artNr, a.getDescription(), a.getPrice(), a.getStock());
			return Response.ok(service.getArticleById(artNr)).build();
		} catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		}

	}

}
