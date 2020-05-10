package service;

import java.util.List;

import javax.ejb.Local;

import domain.Article;
import domain.Customer;
import domain.Order;

@Local
public interface OlfService {

	public List<Order> findAllOrders();
	public List<Customer> findAllCustomer();
	public List<Article> findAllArticle();

	public void register(Article article);
	public void register(Order order);
	public void register(Customer customer);
}
