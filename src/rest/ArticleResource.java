package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.Article;
import service.OlfService;

@Stateless
@Path("/articles")
public class ArticleResource {
	
	@Inject
	private OlfService service;
	
	@GET
	@Produces({"application/JSON", "application/XML"})
	public Response getAllArticles() {
		return Response.ok(service.getAllArticle()).build();
	}
	
	@POST
	@Produces({"application/JSON", "application/XML"})
	@Consumes({"application/JSON", "application/XML"})
	public Article registerArticle(Article article) {
		service.register(article);
		return article;
	}

}
