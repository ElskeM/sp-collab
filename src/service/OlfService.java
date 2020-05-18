package service;

import java.util.List;

import javax.ejb.Local;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.OrderNotFoundException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Local
public interface OlfService {

	public List<CustomerOrder> getAllOrders();
	public List<Customer> getAllCustomer();
	public List<Article> getAllArticle();
	
	public Article getArticle(int artNr) throws ArticleNotFoundException;
	public List<Article> getArticlesBetweenId(int firstId, int secondId);
	public List<Customer> getCustomersBetweenId(int firstId, int secondId);
	public List<CustomerOrder> getCustomerOrdersBetweenId(int firstId, int secondId);
	public Customer getCustomer(int cnr) throws CustomerNotFoundException;
	public CustomerOrder getOrder(int orderNr) throws OrderNotFoundException; 

	public Article register(Article article);
	public void register(CustomerOrder order) throws ArticleNotFoundException, CustomerNotFoundException;
	public void register(Customer customer);
	
	public void dropAllTables();
}
