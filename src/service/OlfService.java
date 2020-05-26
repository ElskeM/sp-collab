package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.ForbiddenDeleteException;
import dao.OrderNotFoundException;
import dao.OutOfStockException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Local
public interface OlfService {

	public List<CustomerOrder> getAllOrders();
	public List<Customer> getAllCustomer();
	public List<Article> getAllArticle();
	
	public Article getArticleById(int artNr) throws ArticleNotFoundException;
	public List<Article> getArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException;
	public List<CustomerOrder> getOrdersBetweenId(int firstId, int secondId) throws OrderNotFoundException;
	public List<Customer> getCustomersBetweenId(int firstId, int secondId) throws CustomerNotFoundException;
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException;
	public List<Customer> getCustomerByName(String name) throws CustomerNotFoundException;
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException; 

	public Article register(Article article);
	public CustomerOrder register(CustomerOrder order) throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException, OutOfStockException ;
	public Customer register(Customer customer);
	
	public void dropAllTables();

	public void deleteArticle(int artNr) throws ArticleNotFoundException;
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;
	
	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate) throws OrderNotFoundException;
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException;
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException;


}
