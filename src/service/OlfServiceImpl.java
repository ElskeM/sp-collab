package service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.DataAccess;
import dao.DataAccessException;
import dao.ForbiddenDeleteException;
import dao.OrderNotFoundException;
import dao.OutOfStockException;
import dao.TestingDao;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

/**
 * @author Peter, Pontus, Simon, Elske
 *
 */
@Stateless
public class OlfServiceImpl implements OlfService {

	@Inject
	/* Vill man testa clienten med DataAccessTestingVersion, ta bort // framför @TestingDao */
	//@TestingDao
	private DataAccess dao;

	@Resource
	private SessionContext ctx;

	@Override
	public List<CustomerOrder> getAllOrders() throws DataAccessException {
		return dao.findAllOrders();
	}

	@Override
	public List<Customer> getAllCustomer() throws DataAccessException {

		return dao.findAllCustomer();
	}

	@Override
	public List<Article> getAllArticle() throws DataAccessException {

		return dao.findAllArticle();
	}

	@Override
	public Article register(Article article) throws DataAccessException{

		dao.insert(article);
		return article;

	}

	@Override
	public CustomerOrder register(CustomerOrder customerOrder)
			throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException, OutOfStockException, DataAccessException {
		dao.insert(customerOrder);
		return customerOrder;
	}

	@Override
	public Customer register(Customer customer) throws ServiceUnavailableException, DataAccessException {
			dao.insert(customer);
			ReceiptSendingService.sendReciept(customer.getFirstName(), customer.getLastName(),
					customer.getAddress(), customer.getZipCode(), customer.getCity());
			return customer;
	}

	@Override
	public void dropAllTables() throws DataAccessException {
		dao.dropAllTables();

	}

	@Override
	public Article getArticleById(int artNr) throws ArticleNotFoundException, DataAccessException {
			return dao.findArticleById(artNr);

	}

	@Override
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException, DataAccessException {
			return dao.findCustomerById(cnr);

	}

	@Override
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException, DataAccessException {
		return dao.findOrderById(orderNr);
	}

	@Override
	public List<Article> getArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException, DataAccessException {

		return dao.findArticlesBetweenId(firstId, secondId);
	}

	@Override
	public List<CustomerOrder> getOrdersBetweenDates(String firstDate, String secondDate) throws OrderNotFoundException, DataAccessException {
		return dao.findOrdersBetweenDates(firstDate, secondDate);
	}

	@Override

	public void deleteArticle(int artNr) throws ArticleNotFoundException, DataAccessException {
		dao.deleteArticle(artNr);

	}

	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate)
			throws OrderNotFoundException, OutOfStockException, ArticleNotFoundException, DataAccessException {
		dao.updateCustomerOrder(orderNr, articles, dispatchDate);

	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException, DataAccessException {
		dao.deleteCustomerOrder(orderNr);
	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException, DataAccessException {
			dao.deleteCustomer(cnr);
	}

	@Override
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException, DataAccessException {
			dao.updateArticle(artNr, description, price, stock);				
	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException, DataAccessException {
			dao.updateCustomer(cnr, customer);
	}


	@Override
	public List<Customer> getCustomerByName(String name) throws CustomerNotFoundException, DataAccessException {
		return dao.findCustomerByLastname(name);
	}

}
