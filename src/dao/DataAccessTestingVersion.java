package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Stateless
@TestingDao
public class DataAccessTestingVersion implements DataAccess {

	@Override
	public void insert(Article newArticle) {
		
	}

	@Override
	public void insert(Customer newCustomer) {

	}

	@Override
	public void insert(CustomerOrder newCustomerOrder) {

	}

	@Override
	public List<CustomerOrder> findAllOrders() {
		Customer cl1 = new Customer("Anders", "Anderson", "Andersväg 1A", "414 23", "Andersdal", 0.1);
		Customer cl2 = new Customer("Bertil", "Bertilson", "Bertillan 2B", "424 34", "Bertilholm", 0.15);
		Customer cl3 = new Customer("Cecilia", "Ceciliason", "Ceciliatorget 3C", "434 45", "Ceciliaborg", 0.2);
		
		Article art1 = new Article("Epson EB-580 projector", "Ultra Short Throw Projector", 6, 19999.95);

		HashMap<Article, Integer> or1 = new HashMap<>();
		HashMap<Article, Integer> or2 = new HashMap<>();
		HashMap<Article, Integer> or3 = new HashMap<>();
		
		or1.put(art1, 1);
		or2.put(art1, 2);
		or3.put(art1, 3);
		
		Date ordD1 = new GregorianCalendar(2020, Calendar.APRIL, 24).getTime();
		Date dispD1 = new GregorianCalendar(2020, Calendar.APRIL, 29).getTime();
		Date ordD2 = new GregorianCalendar(2020, Calendar.APRIL, 27).getTime();
		Date dispD2 = new GregorianCalendar(2020, Calendar.MAY, 2).getTime();
		Date ordD3 = new GregorianCalendar(2020, Calendar.MAY, 2).getTime();
		Date dispD3 = new GregorianCalendar(2020, Calendar.MAY, 9).getTime();
		
		CustomerOrder cor1 = new CustomerOrder(ordD1, dispD1, cl1, or1);
		CustomerOrder cor2 = new CustomerOrder(ordD2, dispD2, cl2, or2);
		CustomerOrder cor3 = new CustomerOrder(ordD3, dispD3, cl3, or3);
		
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		orders.add(cor1);
		orders.add(cor2);
		orders.add(cor3);
		
		return orders;
	}

	@Override
	public void dropAllTables() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Customer> findAllCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findAllArticle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article findArticleById(int artNr) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findCustomerBySurname(String name) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findArticlesBetweenId(int firstId, int secondId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findOrdersBetweenId(int firstId, int secondId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, Date dispatchDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticle(int artNr) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArticle(int artNr, String description, int stock, double price) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		
	}



}
