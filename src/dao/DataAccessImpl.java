package dao;

import java.util.List;
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
	public Article insert(Article article) {
		em.persist(article);

		return article; // with updated Id-value
	}
	
	@Override
	public void insert(Customer customer) {
		em.persist(customer);
	}
	
	@Override
	public void insert(CustomerOrder order) {

		for (Article a : order.getArticles().keySet()) {
			Article db = findArticle(a);
			if (db != null) {
				System.out.println(db.name + ":" + db.getArtNr());
				int quantity = order.getArticles().get(a);
				order.getArticles().keySet().remove(a);
				order.getArticles().put(db, quantity);
			} else {
				insert(a); // cascading
			}
		}
		em.persist(order);
	}

	private Article findArticle(Article a) {

		Query q = em.createQuery("select a from Article a where a.artNr=:artNr");
		q.setParameter("artNr", a.getArtNr());
		System.out.println(q);
		Article res = null;
		try {
			res = (Article) q.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Could not find the article in the database");
			return null;
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
	 * @param orderId
	 * @return
	 */
	private List<Entry<Article,Integer>> getOrderRows(int orderId) {

		Query q = em.createQuery("select entry(orderrows) from CustomerOrder o INNER JOIN o.articles orderrows where o.orderNr=:orderNr",java.util.Map.Entry.class);
		q.setParameter("orderNr", orderId);
		List<Entry<Article,Integer>> res = q.getResultList();
		for(Entry<Article,Integer> e: res) {
			System.out.println(e.getKey().getArtNr()+":"+e.getValue());
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

}
