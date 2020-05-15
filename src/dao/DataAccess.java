package dao;

import java.util.List;
import javax.ejb.Local;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Local
public interface DataAccess {
	public Article insert(Article article);
	public void insert(Customer customer);
	public void insert(CustomerOrder customerOrder);
	
	public List<CustomerOrder> findAllOrders();
	
	/**
	 * Remove this from production version
	 */
	public void dropAllTables();	
	
}	
