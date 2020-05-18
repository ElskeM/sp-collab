package domain;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Entity
@XmlRootElement
@Table(name = "tblCustomerOrder")
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = -5152794172107611719L;
	
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderNr")
	private int orderNr;
	
	@XmlElement
	private Date orderDate;
	
	@XmlElement
	private Date dispatchDate;
	
	@XmlElement
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cnr")
	private Customer customer;
	
	@XmlElement
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
	
	public CustomerOrder(Date orderDate, Date dispatchDate, Customer customer, Map<Article, Integer> articles) {

		this.orderDate = orderDate;
		this.dispatchDate = dispatchDate;
		this.customer = customer;
		this.articles = articles;
	}

	public int getOrderNr() {
		return orderNr;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}
	
	public Date getorderDate() {
		return orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Map<Article, Integer> getArticles() {
		return articles;
	}

}
