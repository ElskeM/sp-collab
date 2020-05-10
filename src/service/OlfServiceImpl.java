package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.DataAccess;
import domain.Article;
import domain.Customer;
import domain.CustomerOrder;

@Stateless
public class OlfServiceImpl implements OlfService {

	@Inject
	private DataAccess dao;
	
	@Override
	public List<CustomerOrder> findAllOrders() {
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
	public void register(CustomerOrder customerOrder) {

		// TODO Auto-generated method stub
		dao.insert(customerOrder);
		
	}

	@Override
	public void register(Customer customer) {

		// TODO Auto-generated method stub
		dao.insert(customer);
		
	}
}
