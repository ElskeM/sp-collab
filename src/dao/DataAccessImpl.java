package dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

/**
 * Class for inserting and accessing data from our derby database
 * 
 * @author Peter, Pontus, Elske, Simon
 *
 */
@Stateless
@Default
@ProductionDao
public class DataAccessImpl implements DataAccess {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insert(Article article) {
		em.persist(article);
	}

	@Override
	public void insert(Customer customer) {
		em.persist(customer);
	}

	
	@Override
	public void insert(CustomerOrder order) throws ArticleNotFoundException, OutOfStockException {

		double total = 0;

		for (String artNr : order.getArticles().keySet()) {
			System.out.println("PRE-FIND");
			Article a = findArticle(artNr); // throws ArticleNotFoundException

			System.out.println(a.getName() + ":" + a.getArtNr());
			int quantity = order.getArticles().get(artNr);
			total += a.getPrice() * quantity;

			if (quantity > a.getStock()) {
				throw new OutOfStockException(
						"Article: " + artNr + " [Requested: " + quantity + "] [In stock: " + a.getStock() + "]");
			} else {
				a.setStock(a.getStock() - quantity);
			}
			// order.getArticles().keySet().remove(a);
			// order.getArticles().put(db, quantity);

			// insert(a); // cascading

		}
		System.out.println("PRE-PERSIST");
		// Get a managed instance of the Customer entity, because we can't persist
		// detached entities
		order.setCustomer(em.find(Customer.class, order.getCustomer().getCustomerNr()));
		em.persist(order);
		System.out.println("SET TOTAL");
		order.setTotal(total);
		System.out.println("EXITING INSERT");
	}

	private Article findArticle(String a) throws ArticleNotFoundException {

		Query q = em.createQuery("select a from Article a where a.artNr=:artNr");
		q.setParameter("artNr", Integer.parseInt(a));
		System.out.println(q);
		Article res = null;
		try {
			res = (Article) q.getSingleResult();
		} catch (NoResultException e) {
			throw new ArticleNotFoundException();

		}
		return res;
	}

	@Override
	public List<CustomerOrder> findAllOrders() {

		Query q = em.createQuery("select order from CustomerOrder order");
		System.out.println("TEST");
		List<CustomerOrder> orders = q.getResultList();
		System.out.println(orders.size());
		return orders;
	}

	
	private List<Entry<Article, Integer>> getOrderRows(int orderId) {

		Query q = em.createQuery(
				"select entry(orderrows) from CustomerOrder o INNER JOIN o.articles orderrows where o.orderNr=:orderNr",
				java.util.Map.Entry.class);

		q.setParameter("orderNr", orderId);
		List<Entry<Article, Integer>> res = q.getResultList();
		for (Entry<Article, Integer> e : res) {
			System.out.println(e.getKey().getArtNr() + ":" + e.getValue());
		}
		return res;
	}

	@Override
	public void dropAllTables() {

		Query q = em.createNativeQuery("drop table tblorderrows");
		q.executeUpdate();
		q = em.createNativeQuery("drop table tblarticle");
		q.executeUpdate();
		q = em.createNativeQuery("drop table tblcustomer");
		q.executeUpdate();
		q = em.createNativeQuery("drop table tblcustomerorder");
		q.executeUpdate();

	}

	@Override
	public List<Customer> findAllCustomer() {
		Query q = em.createQuery("select customer from Customer customer");
		List<Customer> customers = q.getResultList();

		return customers;
	}

	@Override
	public List<Article> findAllArticle() {
		Query q = em.createQuery("select article from Article article");
		List<Article> articles = q.getResultList();
		return articles;
	}

	@Override
	public Article findArticleById(int artNr) throws ArticleNotFoundException {

		Query q = em.createQuery("select article from Article article where article.artNr = :artNr");
		q.setParameter("artNr", artNr);
		try {
			return (Article) q.getSingleResult();
		} catch (NoResultException e) {
			throw new ArticleNotFoundException();
		}
	}

	@Override
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException {
		Query q = em.createQuery("select customer from Customer customer where customer.customerNr=:id");
		q.setParameter("id", cnr);
		Customer customer = null;
		try {
			customer = (Customer) q.getSingleResult();
		} catch (NoResultException e) {
			throw new CustomerNotFoundException();

		}
		return customer;
	}

	@Override
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException {
		Query q = em.createQuery("select order from CustomerOrder order where order.orderNr = :orderNr");
		q.setParameter("orderNr", orderNr);
		CustomerOrder order = null;
		try {
			order = (CustomerOrder) q.getSingleResult();
		} catch (NoResultException e) {
			throw new OrderNotFoundException();
		}
		return order;
	}

	@Override
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException {
		Query q = em.createQuery("select article from Article article where article.name = :name");
		q.setParameter("name", name);
		List<Article> articles = q.getResultList();
		if (articles.isEmpty()) {
			throw new ArticleNotFoundException();
		} else {
			return articles;
		}
	}

	@Override
	public List<Customer> findCustomerByLastname(String name) throws CustomerNotFoundException {
		Query q = em.createQuery("select customer from Customer customer where customer.lastName like :name");
		q.setParameter("name", "%" + name + "%");
		List<Customer> customers = q.getResultList();
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException();
		} else {
			return customers;

		}

	}

	@Override
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException {
		Query q = em.createQuery("select order from CustomerOrder order where order.customer.cnr = :cnr");
		q.setParameter("cnr", cnr);
		List<CustomerOrder> orders = q.getResultList();
		if (orders.isEmpty()) {
			throw new OrderNotFoundException();
		} else {
			return orders;
		}
	}

	@Override
	public List<Article> findArticlesBetweenId(int firstId, int secondId) throws ArticleNotFoundException {

		Query q = em.createQuery(
				"select article from Article article where article.artNr >= :first and " + "article.artNr <= :second");
		q.setParameter("first", firstId);
		q.setParameter("second", secondId);
		List<Article> articles = q.getResultList();
		if (articles.isEmpty()) {
			throw new ArticleNotFoundException();
		} else {
			return articles;

		}
	}

	@Override
	public List<CustomerOrder> findOrdersBetweenDates(String firstDate, String secondDate) throws OrderNotFoundException {
		Query q = em.createQuery(
				"select order from CustomerOrder order where order.orderDate >= :first and order.orderDate <= :second");
		q.setParameter("first", firstDate);
		q.setParameter("second", secondDate);
		List<CustomerOrder> orders = q.getResultList();
		if (orders.isEmpty()) {
			throw new OrderNotFoundException();
		}
		return orders;
	}

	@Override
	public void deleteArticle(int artNr) throws ArticleNotFoundException {
		Article art = findArticleById(artNr);
		em.remove(art);

	}

	@Override
	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate)
			throws OrderNotFoundException {
		CustomerOrder cO = findOrderById(orderNr);
		cO.setArticles(articles);
		cO.setDispatchDate(dispatchDate);
	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException {
		CustomerOrder cO = findOrderById(orderNr);
		em.remove(cO);

	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException {
		Customer cust = findCustomerById(cnr);
		cust.setFirstName(customer.getFirstName());
		cust.setLastName(customer.getLastName());
		cust.setAddress(customer.getAddress());
		cust.setZipCode(customer.getZipCode());
		cust.setCity(customer.getCity());
		cust.setDiscount(customer.getDiscount());

	}

	@Override
	public void updateArticle(int artNr, String description, double price, int stock) throws ArticleNotFoundException {
		Article a = findArticleById(artNr);
		a.setDescription(description);
		a.setPrice(price);
		a.setStock(stock);
	}

	@Override
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException {
		try {
			findOrderByCustomerId(cnr);
			Customer cust = findCustomerById(cnr);
			em.remove(cust);
		} catch (OrderNotFoundException e) {
			throw new ForbiddenDeleteException();
		}
	}


}
