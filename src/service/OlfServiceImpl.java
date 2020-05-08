package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.DataAccess;
import domain.Article;
import domain.Customer;
import domain.Order;

@Stateless
public class OlfServiceImpl implements OlfService {

	@Inject
	private DataAccess dao;
	
	@Override
	public List<Order> findAllOrders() {
		return dao.findAllOrders();
	}

	@Override
	public List<Customer> findAllCustomer() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findAllArticle() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(Article article) {

		// TODO Auto-generated method stub
		dao.insert(article);
		
	}

	@Override
	public void register(Order order) {

		// TODO Auto-generated method stub
		dao.insert(order);
		
	}

	@Override
	public void register(Customer customer) {

		// TODO Auto-generated method stub
		dao.insert(customer);
		
	}
}
