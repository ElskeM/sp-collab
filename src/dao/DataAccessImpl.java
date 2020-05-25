package dao;

import java.util.Date;
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

	/*Insertar vi en artikel som kommer ifrån en order i databasen om den inte finns?
	 * är detta rimligt? Vad händer om inte customern finns?
	 */
	@Override
	public void insert(CustomerOrder order) throws ArticleNotFoundException {

		for (Article a : order.getArticles().keySet()) {
			Article db = findArticle(a); // throws ArticleNotFoundException
			
//			System.out.println(db.getName() + ":" + db.getArtNr());
//			int quantity = order.getArticles().get(a);
//			order.getArticles().keySet().remove(a);
//			order.getArticles().put(db, quantity);
			
			//insert(a); // cascading
			
		}
		em.persist(order);
	}

	private Article findArticle(Article a) throws ArticleNotFoundException {

		Query q = em.createQuery("select a from Article a where a.artNr=:artNr");
		q.setParameter("artNr", a.getArtNr());
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
		List<CustomerOrder> orders = q.getResultList();
		return orders;
	}
	
	/**
	 * Hämtar alla orderrader för ett givet ordernummer
	 * 
	 * @param orderId
	 * @return
	 */

	//Behöver vi hämta orderrows?
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
		}catch (NoResultException e) {
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
		}catch (NoResultException e) {
			throw new OrderNotFoundException();
		}
		return order;
	}

	@Override
	public List<Article> findArticleByName(String name) throws ArticleNotFoundException {
		Query q = em.createQuery("select article from Article article where article.name = :name");
		q.setParameter("name", name);
		List<Article> articles = q.getResultList();
		if(articles.isEmpty()) {
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
		if(customers.isEmpty()) {
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
		if(orders.isEmpty()) {
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
		if(articles.isEmpty()) {
			throw new ArticleNotFoundException();
		} else {
			return articles;
			
		}
	}

	@Override
	public List<CustomerOrder> findOrdersBetweenId(int firstId, int secondId) throws OrderNotFoundException {
		Query q = em.createQuery(
				"select order from CustomerOrder order where order.orderNr >= :first and order.orderNr <= :second");
		q.setParameter("first", firstId);
		q.setParameter("second", secondId);
		List<CustomerOrder> orders = q.getResultList();
		if(orders.isEmpty()) {
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
	public void updateCustomerOrder(int orderNr, Map<Article, Integer> articles, String dispatchDate)
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
//	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException {
//		try {
//			if (findOrderByCustomerId(cnr).size() == 0) {
//				Customer cust = findCustomerById(cnr);
//				em.remove(cust);
//			} else {
//				throw new ForbiddenDeleteException();
//			}
//		} catch (OrderNotFoundException e) {
//		}
//
//	}
	
	public void deleteCustomer(int cnr) throws CustomerNotFoundException, ForbiddenDeleteException {
		try {
			findOrderByCustomerId(cnr); 
			Customer cust = findCustomerById(cnr);
			em.remove(cust);
		}
			catch (OrderNotFoundException e) {
				throw new ForbiddenDeleteException();
			} 
		}


	
	

	@Override
	public List<Customer> findCustomersBetweenId(int firstId, int secondId) throws CustomerNotFoundException {
		Query q = em.createQuery(
				"select customer from Customer customer where customer.cnr >= :first and customer.cnr <= :second");
		q.setParameter("first", firstId);
		q.setParameter("second", secondId);
		List<Customer> customers = q.getResultList();
		if(customers.isEmpty()) {
			throw new CustomerNotFoundException();
		}
			
		return customers;
	}

}
