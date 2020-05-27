package dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

/**
 * @author Peter
 *
 */
@Local
public interface DataAccess {
	/**
	 * Inserts a new article into the database.
	 * 
	 * @param article
	 */
	public void insert(Article article);

	/**
	 * Inserts a new customer into the database.
	 * 
	 * @param customer
	 */
	public void insert(Customer customer);

	/**
	 * Inserts a new order into the database.
	 * 
	 * @param customerOrder
	 * @throws ArticleNotFoundException  when trying to order an article that does
	 *                                   not exist.
	 * @throws CustomerNotFoundException when trying make an order with a customer
	 *                                   that does not exist.
	 * @throws OutOfStockException       when trying place an order on an article
	 *                                   which order amount is larger than the
	 *                                   articles stock.
	 */
	public void insert(CustomerOrder customerOrder)
			throws ArticleNotFoundException, CustomerNotFoundException, OutOfStockException;

	/**
	 * Finds and returns all orders.
	 * 
	 * @return
	 */
	public List<CustomerOrder> findAllOrders();

	/**
	 * Finds and returns all customers.
	 * 
	 * @return
	 */
	public List<Customer> findAllCustomer();

	/**
	 * Finds and returns all articles.
	 * 
	 * @return
	 */
	public List<Article> findAllArticle();

	/**
	 * Finds and returns the article with the specific article number.
	 * 
	 * @param artNr
	 * @return
	 * @throws ArticleNotFoundException
	 */
	public Article findArticleById(int artNr) throws ArticleNotFoundException;

	/**
	 * Finds and returns the customer with the specific customer number.
	 * 
	 * @param cnr
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException;

	/**
	 * Finds and returns the order with the specific order number.
	 * 
	 * @param orderNr
	 * @return
	 * @throws OrderNotFoundException
	 */
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException;

	/**
	 * Finds and returns articles which contains the string parameter.
	 * 
	 * @param name
	 * @return
	 * @throws ArticleNotFoundException
	 */
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException;

	/**
	 * Finds all customers whose last name contains the string parameter.
	 * 
	 * @param name of the customer to find.
	 * @return list of customers.
	 * @throws CustomerNotFoundException if no customers was found.
	 */
	public List<Customer> findCustomerByLastname(String name) throws CustomerNotFoundException;

	/**
	 * Finds and returns all orders made by a customer.
	 * 
	 * @param cnr Customer number of the orders to find.
	 * @return list of orders with matching customer number.
	 * @throws OrderNotFoundException if no orders were found.
	 */
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException;

	/**
	 * Finds and returns a list of articles which has an article number between the
	 * two parameters.
	 * 
	 * @param firstId  lowest number.
	 * @param secondId highest number.
	 * @return
	 * @throws ArticleNotFoundException if no articles were found.
	 */
	public List<Article> findArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException;

	/**
	 * Finds and returns a list of orders which has an order number between the two
	 * parameters.
	 * 
	 * @param firstId  lowest number.
	 * @param secondId highest number.
	 * @return
	 * @throws OrderNotFoundException
	 */
	public List<CustomerOrder> findOrdersBetweenDates(String firstDate, String secondDates) throws OrderNotFoundException;

	/**
	 * Finds and returns a list of customers which has an customer number between
	 * the two parameters.
	 * 
	 * @param firstId  lowest number.
	 * @param secondId highest number.
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public List<Customer> findCustomersBetweenId(int firstId, int secondId) throws CustomerNotFoundException;

	/**
	 * Updates an existing order with a new list of articles and dispatch date.
	 * 
	 * @param orderNr
	 * @param articles
	 * @param dispatchDate
	 * @throws OrderNotFoundException
	 */
	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate)
			throws OrderNotFoundException;

	/**
	 * Updates an existing customer.
	 * 
	 * @param cnr
	 * @param customer
	 * @throws CustomerNotFoundException
	 */
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException;

	/**
	 * Updates an existing article with new description, price and stock.
	 * 
	 * @param artNr
	 * @param description
	 * @param price
	 * @param stock
	 * @throws ArticleNotFoundException
	 */
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException;

	/**
	 * Removes a article from the database.
	 * 
	 * @param artNr
	 * @throws ArticleNotFoundException
	 */
	public void deleteArticle(int artNr) throws ArticleNotFoundException;

	/**
	 * 
	 * Removes a order from the database.
	 * 
	 * @param orderNr
	 * @throws OrderNotFoundException
	 */
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException;

	/**
	 * Removes a customer if the customer does not have a order.
	 * 
	 * @param cnr customer number
	 * @throws CustomerNotFoundException
	 * @throws ForbiddenDeleteException
	 */
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException;

	/**
	 * Remove this from production version
	 */
	public void dropAllTables();

}
