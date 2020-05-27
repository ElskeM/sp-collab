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
	public List<CustomerOrder> getAllOrders() {
		return dao.findAllOrders();
	}

	@Override
	public List<Customer> getAllCustomer() {

		return dao.findAllCustomer();
	}

	@Override
	public List<Article> getAllArticle() {

		return dao.findAllArticle();
	}

	@Override
	public Article register(Article article){

		dao.insert(article);
		return article;

	}

	@Override
	public CustomerOrder register(CustomerOrder customerOrder)
			throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException, OutOfStockException {
//		try {
//			dao.insert(customerOrder);
//			Customer customer = customerOrder.getCustomer();
//			recieptSendingService.sendReceipt(customer.getFirstName(), customer.getLastName(), 
//					customer.getAddress(), customer.getZipCode(), customer.getCity());
//			return customerOrder;
//		} catch (ServiceUnavailableException e) {
//			ctx.setRollbackOnly();
//			throw e;
//		}
		dao.insert(customerOrder);
		return customerOrder;

	}

	@Override
	public Customer register(Customer customer) throws ServiceUnavailableException {
			dao.insert(customer);
			ReceiptSendingService.sendReciept(customer.getFirstName(), customer.getLastName(),
					customer.getAddress(), customer.getZipCode(), customer.getCity());
			return customer;
	}

	@Override
	public void dropAllTables() {

		dao.dropAllTables();

	}

	@Override
	public Article getArticleById(int artNr) throws ArticleNotFoundException {
			return dao.findArticleById(artNr);

	}

	@Override
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException {
			return dao.findCustomerById(cnr);

	}

	@Override
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException {
		return dao.findOrderById(orderNr);
	}

	@Override
	public List<Article> getArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException {

		return dao.findArticlesBetweenId(firstId, secondId);
	}

	@Override
	public List<CustomerOrder> getOrdersBetweenDates(String firstDate, String secondDate) throws OrderNotFoundException {
		return dao.findOrdersBetweenDates(firstDate, secondDate);
	}

	@Override

	public void deleteArticle(int artNr) throws ArticleNotFoundException {
		dao.deleteArticle(artNr);

	}

	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate)
			throws OrderNotFoundException, OutOfStockException, ArticleNotFoundException {
		dao.updateCustomerOrder(orderNr, articles, dispatchDate);

	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException {
		dao.deleteCustomerOrder(orderNr);
	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException {
			dao.deleteCustomer(cnr);
	}

	@Override
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException {
			dao.updateArticle(artNr, description, price, stock);				
	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException {
			dao.updateCustomer(cnr, customer);
	}


	@Override
	public List<Customer> getCustomerByName(String name) throws CustomerNotFoundException {
		return dao.findCustomerByLastname(name);
	}


}
