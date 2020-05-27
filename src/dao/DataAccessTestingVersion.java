package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

/**
 * @author elske
 *
 */
@Stateless
@TestingDao
public class DataAccessTestingVersion implements DataAccess {

	/**
	 * @author elske
	 */
	@Override
	public void insert(Article newArticle) {
		Article art1 = new Article("Epson EB-580 projector", "Ultra Short Throw Projector", 6, 19999.95);
	}

	/**
	 * @author elske
	 */
	@Override
	public void insert(Customer newCustomer) {
		Customer cl1 = new Customer("Anders", "Anderson", "Andersväg 1A", "414 23", "Andersdal", 0.1);
	}

	/**
	 * @author elske
	 */
	@Override
	public void insert(CustomerOrder newCustomerOrder) {
		Customer cl2 = new Customer("Bertil", "Bertilson", "Bertillan 2B", "424 34", "Bertilholm", 0.15);
		Article art1 = new Article("Epson EB-580 projector", "Ultra Short Throw Projector", 6, 19999.95);
		HashMap<String, Integer> or2 = new HashMap<>();
		or2.put("" + art1.getArtNr(), 2);
		String ordD2 = "2020-04-27";
		String dispD2 = "2020-05-02";
		CustomerOrder cor2 = new CustomerOrder(ordD2, dispD2, cl2, or2);
	}

	/**
	 * @author elske
	 */
	@Override
	public List<CustomerOrder> findAllOrders() {
		Customer cl1 = new Customer("Anders", "Anderson", "Andersväg 1A", "414 23", "Andersdal", 0.1);
		Customer cl2 = new Customer("Bertil", "Bertilson", "Bertillan 2B", "424 34", "Bertilholm", 0.15);
		Customer cl3 = new Customer("Cecilia", "Ceciliason", "Ceciliatorget 3C", "434 45", "Ceciliaborg", 0.2);

		Article art1 = new Article("Epson EB-580 projector", "Ultra Short Throw Projector", 6, 19999.95);

		HashMap<String, Integer> or1 = new HashMap<>();
		HashMap<String, Integer> or2 = new HashMap<>();
		HashMap<String, Integer> or3 = new HashMap<>();

		or1.put("" + art1.getArtNr(), 1);
		or2.put("" + art1.getArtNr(), 2);
		or3.put("" + art1.getArtNr(), 3);

		String ordD1 = "2020-04-24";
		String dispD1 = "2020-04-29";
		String ordD2 = "2020-04-27";
		String dispD2 = "2020-05-02";
		String ordD3 = "2020-05-02";
		String dispD3 = "2020-05-09";

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

	/**
	 * @author elske
	 */
	@Override
	public List<Customer> findAllCustomer() {
		Customer cl1 = new Customer("Anders", "Anderson", "Andersväg 1A", "414 23", "Andersdal", 0.1);
		Customer cl2 = new Customer("Bertil", "Bertilson", "Bertillan 2B", "424 34", "Bertilholm", 0.15);
		Customer cl3 = new Customer("Cecilia", "Ceciliason", "Ceciliatorget 3C", "434 45", "Ceciliaborg", 0.2);
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(cl1);
		customers.add(cl2);
		customers.add(cl3);
		return customers;
	}

	/**
	 * @author elske
	 */
	@Override
	public List<Article> findAllArticle() {
		Article art1 = new Article("Epson EB-580 projector", "Ultra Short Throw Projector", 6, 19999.95);
		Article art2 = new Article("Lenovo ThinkPad T490", "Ideal laptop for the office", 3, 14999.00);
		Article art3 = new Article("Samsung monitor", "Curved screen for an optimal experience", 9, 1998.97);
		List<Article> articles = new ArrayList<Article>();
		articles.add(art1);
		articles.add(art2);
		articles.add(art3);
		return articles;
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
	public List<Customer> findCustomerByLastname(String name) throws CustomerNotFoundException {
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
	public List<CustomerOrder> findOrdersBetweenDates(String firstDate, String secondDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate) {
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
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
	}

}
