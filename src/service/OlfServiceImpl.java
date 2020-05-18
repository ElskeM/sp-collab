package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.ArticleNotFoundException;
import dao.CustomerNotFoundException;
import dao.DataAccess;
import dao.OrderNotFoundException;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Stateless
public class OlfServiceImpl implements OlfService {

	@Inject
	private DataAccess dao;

	@Resource
	private SessionContext ctx;

	@Override
	public List<CustomerOrder> getAllOrders() throws ServiceUnavailableException {
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
	public Article register(Article article) {

		dao.insert(article);
		return article;

	}

	@Override
	public CustomerOrder register(CustomerOrder customerOrder)
			throws ArticleNotFoundException, CustomerNotFoundException, ServiceUnavailableException {

		dao.insert(customerOrder);
		return customerOrder;

	}

	@Override
	public Customer register(Customer customer) throws ServiceUnavailableException {
		dao.insert(customer);
		return customer;
	}

	@Override
	public void dropAllTables() {

		dao.dropAllTables();

	}

	@Override
	public Article getArticleById(int artNr) throws ArticleNotFoundException {

		try {
			Article art = dao.findArticleById(artNr);
			return art;
		} catch (Exception e) {
			throw new ArticleNotFoundException();

		}
	}

	@Override
	public Customer getCustomerById(int cnr) throws CustomerNotFoundException {
		try {
			return dao.findCustomerById(cnr);
		} catch (Exception e) {
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public CustomerOrder getOrderById(int orderNr) throws OrderNotFoundException {
		return dao.findOrderById(orderNr);
	}

	@Override
	public List<Article> getArticlesBetweenId(int firstId, int secondId) {

		return dao.findArticlesBetweenId(firstId, secondId);
	}

	@Override
	public List<Customer> getCustomersBetweenId(int firstId, int secondId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public void deleteArticle(int artNr) throws ArticleNotFoundException {
		dao.deleteArticle(artNr);

	}

	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate)
			throws OrderNotFoundException {
		dao.updateCustomerOrder(orderNr, articles, dispatchDate);

	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException {

	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException {
		try {
			dao.deleteCustomer(cnr);
		} catch (Exception e) {
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public void updateArticle(int artNr, String description, int stock, double price) throws ArticleNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException {
		try {
			dao.updateCustomer(cnr, customer);
		} catch (Exception e) {
			throw new CustomerNotFoundException();
		}
	}

}
