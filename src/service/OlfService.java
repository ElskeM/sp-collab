package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.OrderNotFoundException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Local
public interface OlfService {

	public List<CustomerOrder> getAllOrders() throws ServiceUnavailableException;
	public List<Customer> getAllCustomer();
	public List<Article> getAllArticle();
	
	public Article getArticleById(int artNr) throws ArticleNotFoundException;
	public List<Article> getArticlesBetweenId(int firstId, int secondId);
	public List<Customer> getCustomersBetweenId(int firstId, int secondId);
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException;
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException; 

	public Article register(Article article);
	public CustomerOrder register(CustomerOrder order) throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException ;
	public Customer register(Customer customer) throws ServiceUnavailableException;
	
	public void dropAllTables();
	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate) throws OrderNotFoundException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;

}
