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

	void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate) throws OrderNotFoundException;
	void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;
	
	
	
	
	/**
	 * Remove this from production version
	 */
	public void dropAllTables();	
	
}	
