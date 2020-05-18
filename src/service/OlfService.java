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
	public List<Customer> getAllCustomer() throws ServiceUnavailableException;
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

	public void deleteArticle(int artNr) throws ArticleNotFoundException;
	public void deleteCustomer(int cnr) throws CustomerNotFoundException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;
	
	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate) throws OrderNotFoundException;
	public void updateArticle(int artNr, String description, int stock, double price ) throws ArticleNotFoundException;
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException;

}
