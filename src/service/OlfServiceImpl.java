package service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.DataAccess;
import dao.OrderNotFoundException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;



@Stateless
public class OlfServiceImpl implements OlfService  {

	@Inject
	private DataAccess dao;
	
	@Resource
	private SessionContext ctx;
	
	@Override
	public List<CustomerOrder> getAllOrders() throws ServiceUnavailableException {
		return dao.findAllOrders();
	}

	@Override
	public List<Customer> getAllCustomer() {

		return dao.findAllCustomer();
	}

	@Override
	public List<Article> getAllArticle() {

		return dao.findAllArticle();
	}

	@Override
	public Article register(Article article) {

		dao.insert(article);
		return article;
		
	}

	@Override
	public CustomerOrder register(CustomerOrder customerOrder) throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException {

			dao.insert(customerOrder);
			return customerOrder;
		
	}

	@Override
	public Customer register(Customer customer) {

		// TODO Auto-generated method stub
		dao.insert(customer);	
		return customer;
	}
	
	@Override
	public void dropAllTables() {

		dao.dropAllTables();
		
	}

	@Override
	public Article getArticle(int artNr) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(int cnr) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerOrder getOrder(int orderNr) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> getArticlesBetweenId(int firstId, int secondId) {

		return dao.findArticlesBetweenId(firstId, secondId);
	}

	@Override
	public List<Customer> getCustomersBetweenId(int firstId, int secondId) {
		// TODO Auto-generated method stub
		return null;
	}

}
