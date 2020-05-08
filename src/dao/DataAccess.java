package dao;

import java.util.List;

import javax.ejb.Local;

import domain.Article;
import domain.Customer;
import domain.Order;

@Local
public interface DataAccess {
	public void insert(Article article);
	public void insert(Customer customer);
	public void insert(Order order);
	
	public List<Order> findAllOrders();
}	
