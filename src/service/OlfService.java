package service;

import java.util.List;
import javax.ejb.Remote;

import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Remote
public interface OlfService {

	public List<CustomerOrder> findAllOrders();
	public List<Customer> findAllCustomer();
	public List<Article> findAllArticle();

	public Article register(Article article);
	public void register(CustomerOrder order);
	public void register(Customer customer);
	
	public void dropAllTables();
}
