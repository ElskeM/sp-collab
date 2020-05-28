package service;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.DataAccessException;
import dao.ForbiddenDeleteException;
import dao.OrderNotFoundException;
import dao.OutOfStockException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

/**
 * @author Peter, Pontus, Simon, Elske
 *
 */
@Local
public interface OlfService {

	public List<CustomerOrder> getAllOrders() throws DataAccessException;
	public List<Customer> getAllCustomer() throws DataAccessException;
	public List<Article> getAllArticle() throws DataAccessException;
	
	public Article getArticleById(int artNr) throws ArticleNotFoundException, DataAccessException;
	public List<Article> getArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException, DataAccessException;
	public List<CustomerOrder> getOrdersBetweenDates(String firstDate, String secondDate) throws OrderNotFoundException, DataAccessException;
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException, DataAccessException;
	public List<Customer> getCustomerByName(String name) throws CustomerNotFoundException, DataAccessException;
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException, DataAccessException; 

	public Article register(Article article) throws DataAccessException;
	public CustomerOrder register(CustomerOrder order) throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException, OutOfStockException, DataAccessException ;
	public Customer register(Customer customer) throws ServiceUnavailableException, DataAccessException;
	
	public void dropAllTables() throws DataAccessException;

	public void deleteArticle(int artNr) throws ArticleNotFoundException, DataAccessException;
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException, DataAccessException;
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException, DataAccessException;
	
	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate) throws OrderNotFoundException, OutOfStockException, ArticleNotFoundException, DataAccessException;
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException, DataAccessException;
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException, DataAccessException;


}
