package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

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
	public void insert(CustomerOrder customerOrder) {
		em.persist(customerOrder);
	}

	@Override
	public List<CustomerOrder> findAllOrders() {
		Query q = em.createQuery("select order from CustomerOrder order");
		List<CustomerOrder> customerOrders = q.getResultList();
		return customerOrders;
	}
	
}
