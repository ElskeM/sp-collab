package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

import dao.DataAccess;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Stateless
@WebService(name="Pantheon")
public class OlfWebserviceImplementation{
	
	@Inject
	private OlfService service;
	
	
	public List<CustomerOrder> findAllOrders() {
		return service.findAllOrders();
	}


	public List<Customer> findAllCustomer() {

		// TODO Auto-generated method stub
		return service.findAllCustomer();
	}


	public List<Article> findAllArticle() {

		// TODO s-generated method stub
		return service.findAllArticle();
	}

	public Article register(Article article) {

		// TODO Auto-generated method stub
		return service.register(article);
		
	}

	
	public void register(CustomerOrder customerOrder) {

		// TODO Auto-generated method stub
		service.register(customerOrder);		
	}


	public void register(Customer customer) {

		// TODO Auto-generated method stub
		service.register(customer);	
	}
	

	public void dropAllTables() {

		service.dropAllTables();
	}
	
	
	
	

}
