package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Article;
import domain.Customer;
import domain.Order;

@Stateless
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
	public void insert(Order order) {
		em.persist(order);
	}

	@Override
	public List<Order> findAllOrders() {
		Query q = em.createQuery("select order from Order order");
		List<Order> orders = q.getResultList();
		return orders;
	}
	
}
