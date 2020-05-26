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
	public void insert(CustomerOrder customerOrder) throws ArticleNotFoundException,CustomerNotFoundException, OutOfStockException;
	
	public List<CustomerOrder> findAllOrders();
	public List<Customer> findAllCustomer();
	public List<Article> findAllArticle();
	
	public Article findArticleById(int artNr) throws ArticleNotFoundException;
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException;
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException; 
	
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException;
	public List<Customer> findCustomerByLastname(String name) throws CustomerNotFoundException;//Or just name and search for both first- and surname?
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException;
	
	public List<Article> findArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException;
	public List<CustomerOrder> findOrdersBetweenId(int firstId, int secondId) throws OrderNotFoundException;
	public List<Customer> findCustomersBetweenId(int firstId, int secondId) throws CustomerNotFoundException;


	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate) throws OrderNotFoundException;
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException;
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException;
	
	public void deleteArticle(int artNr) throws ArticleNotFoundException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;
	/**
	 * Tar bort en kund om kunden inte har en order.
	 * @param cnr Kundnummer
	 * @throws CustomerNotFoundException
	 * @throws ForbiddenDeleteException
	 */
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException;

	
	
	
	
	/**
	 * Remove this from production version
	 */
	public void dropAllTables();	
	
}	
