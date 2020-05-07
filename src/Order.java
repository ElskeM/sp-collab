import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderNr;
	private Date deliveryDate;// the date the package was sent. TODO: Change name to something better!!!
	private Customer customer;
	private HashMap<Article, Integer> articles = new HashMap<Article, Integer>();

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(Date deliveryDate, Customer customer, HashMap<Article, Integer> articles) {

		this.deliveryDate = deliveryDate;
		this.customer = customer;
		this.articles = articles;
	}

	public int getOrderNr() {
		return orderNr;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public HashMap<Article, Integer> getArticles() {
		return articles;
	}

}
