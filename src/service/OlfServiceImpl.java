package service;

import java.util.List;

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
	
	@Override
	public List<CustomerOrder> getAllOrders() {
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

		// TODO Auto-generated method stub
		return dao.insert(article);
		
	}

	@Override
	public void register(CustomerOrder customerOrder) throws ArticleNotFoundException, CustomerNotFoundException {

			dao.insert(customerOrder);
		
	}

	@Override
	public void register(Customer customer) {

		// TODO Auto-generated method stub
		dao.insert(customer);		
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
}
