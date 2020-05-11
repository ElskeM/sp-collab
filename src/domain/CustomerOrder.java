package domain;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tblCustomerOrder")
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = -5152794172107611719L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderNr")
	private int orderNr;
	private Date deliveryDate;// the date the package was sent. TODO: Change name to something better!!!
	private Customer customer;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tblOrderRows",
			joinColumns = {@JoinColumn(name = "orderNr", referencedColumnName = "orderNr")})
	@Column(name = "antal")
	@MapKeyJoinColumn(name = "artNr")
	@MapKeyColumn(name = "orderNr")
	private Map<Article, Integer> articles;

	public CustomerOrder() {
		// TODO Auto-generated constructor stub
	}

	public CustomerOrder(Date deliveryDate, Customer customer, Map<Article, Integer> articles) {

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

	public Map<Article, Integer> getArticles() {
		return articles;
	}

}
