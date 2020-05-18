package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Local
public interface DataAccess {
	public void insert(Article article);
	public void insert(Customer customer);
	public void insert(CustomerOrder customerOrder) throws ArticleNotFoundException,CustomerNotFoundException;
	
	public List<CustomerOrder> findAllOrders();
	public List<Customer> findAllCustomer();
	public List<Article> findAllArticle();
	
	public Article findArticleById(int artNr) throws ArticleNotFoundException;
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException;
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException; 
	
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException;
	public List<Customer> findCustomerBySurname(String name) throws CustomerNotFoundException;//Or just name and search for both first- and surname?
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException;
	
	public List<Article> findArticlesBetweenId(int firstId, int secondId);
	public List<Customer> findOrdersBetweenId(int firstId, int secondId);


	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate) throws OrderNotFoundException;
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException;
	public void updateArticle(int artNr, String description, int stock, double price ) throws ArticleNotFoundException;
	
	public void deleteArticle(int artNr) throws ArticleNotFoundException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;
	public void deleteCustomer(int cnr) throws CustomerNotFoundException;

	
	
	
	
	/**
	 * Remove this from production version
	 */
	public void dropAllTables();	
	
}	
