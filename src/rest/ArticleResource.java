package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import dao.ArticleNotFoundException;
import domain.Article;
import service.OlfService;

@Stateless
@Path("/articles")
public class ArticleResource {
	
	@Inject
	private OlfService service;
	
//	@GET
//	@Produces({"application/JSON", "application/XML"})
//	public Response getAllArticles() {
//		return Response.ok(service.getAllArticle()).build();
//	}
	
	@GET
	@Produces({"application/JSON", "application/XML"})
	public Response getAllArticlesBetweenId(@DefaultValue("0") @QueryParam("firstId") Integer firstId, 
			@QueryParam("secondId") Integer secondId) {
		if(firstId == 0 && secondId == null) {
			GenericEntity<List<Article>> articles = 
					new GenericEntity<List<Article>>(service.getAllArticle()) {};
			return Response.ok(articles).build();
		}
		if(firstId != null && secondId != null) {
			GenericEntity<List<Article>> articles = 
					new GenericEntity<List<Article>>(service.getArticlesBetweenId(firstId, secondId)) {};
			return Response.ok(articles).build();
		}
		return Response.status(400).build();
		
	}
	
	@GET
	@Produces({"application/JSON", "application/XML"})
	@Path("{artNr")
	public Response getArticleById(@PathParam("artNr") int artNr) {
		try {
			Article art = service.getArticleById(artNr);
			return Response.ok(art).build();
		}catch (ArticleNotFoundException e) {
			return Response.status(404).build();
		}
	}
	
	
	@POST
	@Produces({"application/JSON", "application/XML"})
	@Consumes({"application/JSON", "application/XML"})
	public Article registerArticle(Article article) {
		service.register(article);
		return article;
	}

}
