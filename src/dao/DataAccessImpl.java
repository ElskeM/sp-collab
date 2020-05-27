package dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
	public void insert(Article article) throws DataAccessException {
		try {
			em.persist(article);
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling insert(Article)", e);
		}
	}

	@Override
	public void insert(Customer customer) throws DataAccessException {
		try {
			em.persist(customer);
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling insert(Customer)", e);
		}
	}

	@Override
	public void insert(CustomerOrder order) throws ArticleNotFoundException, OutOfStockException, DataAccessException {

		double total = 0;

		for (String artNr : order.getArticles().keySet()) {
			System.out.println("PRE-FIND");
			Article a = findArticle(artNr); // throws ArticleNotFoundException, DataAccessException

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
		try {
			// Get a managed instance of the Customer entity, because we can't persist
			// detached entities
			order.setCustomer(em.find(Customer.class, order.getCustomer().getCustomerNr()));
			em.persist(order);
			System.out.println("SET TOTAL");
			order.setTotal(total);
			System.out.println("EXITING INSERT");
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling insert(CustomerOrder)", e);
		}
	}

	private Article findArticle(String a) throws ArticleNotFoundException, DataAccessException {

		Query q = em.createQuery("select a from Article a where a.artNr=:artNr");
		q.setParameter("artNr", Integer.parseInt(a));
		System.out.println(q);
		Article res = null;
		try {
			return (Article) q.getSingleResult();
		} catch (NoResultException e) {
			throw new ArticleNotFoundException();
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findArticle()", e);
		}
	}

	@Override
	public List<CustomerOrder> findAllOrders() throws DataAccessException {

		Query q = em.createQuery("select order from CustomerOrder order");
		System.out.println("TEST");
		try {
			List<CustomerOrder> orders = q.getResultList();
			System.out.println(orders.size());
			return orders;
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findAllOrders()", e);
		}
	}

	/**
	 * Hämtar alla orderrader för ett givet ordernummer
	 * 
	 * @param orderId
	 * @return
	 */

	// Behöver vi hämta orderrows?
	private List<Entry<Article, Integer>> getOrderRows(int orderId) throws DataAccessException {

		try {
			Query q = em.createQuery(
					"select entry(orderrows) from CustomerOrder o INNER JOIN o.articles orderrows where o.orderNr=:orderNr",
					java.util.Map.Entry.class);

			q.setParameter("orderNr", orderId);
			List<Entry<Article, Integer>> res = q.getResultList();
			for (Entry<Article, Integer> e : res) {
				System.out.println(e.getKey().getArtNr() + ":" + e.getValue());
			}
			return res;
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling getOrderRows(int)", e);
		}
	}

	@Override
	public void dropAllTables() throws DataAccessException {

		try {
			Query q = em.createNativeQuery("drop table tblorderrows");
			q.executeUpdate();
			q = em.createNativeQuery("drop table tblarticle");
			q.executeUpdate();
			q = em.createNativeQuery("drop table tblcustomer");
			q.executeUpdate();
			q = em.createNativeQuery("drop table tblcustomerorder");
			q.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException("Internal server error calling dropAllTables()", e);
		}

	}

	@Override
	public List<Customer> findAllCustomer() throws DataAccessException {
		try {
			Query q = em.createQuery("select customer from Customer customer");
			List<Customer> customers = q.getResultList();

			return customers;
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findAllCustomer()");
		}
	}

	@Override
	public List<Article> findAllArticle() throws DataAccessException {
		try {
			Query q = em.createQuery("select article from Article article");
			List<Article> articles = q.getResultList();
			return articles;
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findAllArticle()", e);
		}
	}

	@Override
	public Article findArticleById(int artNr) throws ArticleNotFoundException, DataAccessException {

		Query q = em.createQuery("select article from Article article where article.artNr = :artNr");
		q.setParameter("artNr", artNr);
		try {
			return (Article) q.getSingleResult();
		} catch (NoResultException e) {
			throw new ArticleNotFoundException();
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findArticleById(int)", e);
		}
	}

	@Override
	public Customer findCustomerById(int cnr) throws CustomerNotFoundException, DataAccessException {
		Query q = em.createQuery("select customer from Customer customer where customer.customerNr=:id");
		q.setParameter("id", cnr);
		Customer customer = null;
		try {
			return (Customer) q.getSingleResult();
		} catch (NoResultException e) {
			throw new CustomerNotFoundException();
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findCustomerById(int)", e);
		}
	}

	@Override
	public CustomerOrder findOrderById(int orderNr) throws OrderNotFoundException, DataAccessException {
		Query q = em.createQuery("select order from CustomerOrder order where order.orderNr = :orderNr");
		q.setParameter("orderNr", orderNr);
		CustomerOrder order = null;
		try {
			return (CustomerOrder) q.getSingleResult();
		} catch (NoResultException e) {
			throw new OrderNotFoundException();
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findOrderById(int)", e);
		}
	}

	@Override
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException, DataAccessException {
		Query q = em.createQuery("select article from Article article where article.name = :name");
		q.setParameter("name", name);
		try {
			List<Article> articles = q.getResultList();
			if (articles.isEmpty()) {
				throw new ArticleNotFoundException();
			} else {
				return articles;
			}
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findArticleByName(String)", e);
		}
	}

	@Override
	public List<Customer> findCustomerByLastname(String name) throws CustomerNotFoundException, DataAccessException {
		Query q = em.createQuery("select customer from Customer customer where customer.lastName like :name");
		q.setParameter("name", "%" + name + "%");
		try {
			List<Customer> customers = q.getResultList();
			if (customers.isEmpty()) {
				throw new CustomerNotFoundException();
			} else {
				return customers;

			}
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findCustomerByLastname(String)", e);
		}

	}

	@Override
	public List<CustomerOrder> findOrderByCustomerId(int cnr) throws OrderNotFoundException, DataAccessException {
		Query q = em.createQuery("select order from CustomerOrder order where order.customer.cnr = :cnr");
		q.setParameter("cnr", cnr);
		try {
			List<CustomerOrder> orders = q.getResultList();
			if (orders.isEmpty()) {
				throw new OrderNotFoundException();
			} else {
				return orders;
			}
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findOrderByCustomerId(int)", e);
		}
	}

	@Override
	public List<Article> findArticlesBetweenId(int firstId, int secondId)
			throws ArticleNotFoundException, DataAccessException {

		Query q = em.createQuery(
				"select article from Article article where article.artNr >= :first and " + "article.artNr <= :second");
		q.setParameter("first", firstId);
		q.setParameter("second", secondId);
		try {
			List<Article> articles = q.getResultList();
			if (articles.isEmpty()) {
				throw new ArticleNotFoundException();
			} else {
				return articles;
			}
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findArticlesBetweenId(int,int)", e);
		}
	}

	@Override
	public List<CustomerOrder> findOrdersBetweenDates(String firstDate, String secondDate)
			throws OrderNotFoundException, DataAccessException {
		Query q = em.createQuery(
				"select order from CustomerOrder order where order.orderDate >= :first and order.orderDate <= :second");
		q.setParameter("first", firstDate);
		q.setParameter("second", secondDate);
		try {
			List<CustomerOrder> orders = q.getResultList();
			if (orders.isEmpty()) {
				throw new OrderNotFoundException();
			}
			return orders;
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling findOrdersBetweenDates(String,String)", e);
		}
	}

	@Override
	public void deleteArticle(int artNr) throws ArticleNotFoundException, DataAccessException {
		Article art = findArticleById(artNr);
		try {
			em.remove(art);
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling deleteArticle(int)", e);
		}

	}

	@Override
	public void updateCustomerOrder(int orderNr, Map<String, Integer> articles, String dispatchDate)
			throws OrderNotFoundException, OutOfStockException, ArticleNotFoundException, DataAccessException {
		double total = 0;

		try {
			for (String artNr : articles.keySet()) {
				System.out.println("PRE-FIND");
				Article a = findArticle(artNr); // throws ArticleNotFoundException, DataAccessException

				System.out.println(a.getName() + ":" + a.getArtNr());
				int quantity = articles.get(artNr);
				total += a.getPrice() * quantity;

				if (quantity > a.getStock()) {
					throw new OutOfStockException(
							"Article: " + artNr + " [Requested: " + quantity + "] [In stock: " + a.getStock() + "]");
				} else {
					a.setStock(a.getStock() - quantity);
				}
			}
			System.out.println("PRE-PERSIST");
			CustomerOrder cO = findOrderById(orderNr); // throws checked exceptions
			cO.setTotal(0.0);
			cO.setArticles(articles);
			cO.setDispatchDate(dispatchDate);
			System.out.println("SET TOTAL");
			cO.setTotal(total);
			System.out.println("EXITING INSERT");
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling updateCustomerOrder(int,Map,String)", e);
		}
	}

	@Override
	public void deleteCustomerOrder(int orderNr) throws OrderNotFoundException, DataAccessException {
		CustomerOrder cO = findOrderById(orderNr);
		try {
			em.remove(cO);
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling deleteCustomerOrder(int)", e);
		}

	}

	@Override
	public void updateCustomer(int cnr, Customer customer) throws CustomerNotFoundException, DataAccessException {
		Customer cust = findCustomerById(cnr);
		try {
			cust.setFirstName(customer.getFirstName());
			cust.setLastName(customer.getLastName());
			cust.setAddress(customer.getAddress());
			cust.setZipCode(customer.getZipCode());
			cust.setCity(customer.getCity());
			cust.setDiscount(customer.getDiscount());
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling updateCustomer(int, Customer)", e);
		}

	}

	@Override
	public void updateArticle(int artNr, String description, double price, int stock)
			throws ArticleNotFoundException, DataAccessException {
		Article a = findArticleById(artNr);
		try {
			a.setDescription(description);
			a.setPrice(price);
			a.setStock(stock);
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling updateArticle(int,String,double,int)", e);
		}
	}

	@Override
	public void deleteCustomer(int cnr)
			throws CustomerNotFoundException, ForbiddenDeleteException, DataAccessException {
		try {
			findOrderByCustomerId(cnr);
			Customer cust = findCustomerById(cnr);
			em.remove(cust);
		} catch (OrderNotFoundException e) {
			throw new ForbiddenDeleteException();
		} catch (PersistenceException e) {
			throw new DataAccessException("Internal server error calling deleteCustomer(int)", e);
		}
	}

}
