package domain;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "tblCustomerOrder")
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = -5152794172107611719L;
	
	@Transient
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderNr")
	@XmlAttribute
	private int orderNr;

	@XmlElement
	private String orderDate;
	
	@XmlElement
	private String dispatchDate;


	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cnr")
	@XmlElement
	private Customer customer;
	

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tblOrderRows",
			joinColumns = {@JoinColumn(name = "orderNr", referencedColumnName = "orderNr")})
	@Column(name = "antal")
	@MapKeyJoinColumn(name = "artNr")
	@MapKeyColumn(name = "orderNr")
	@XmlElement
	private Map<Article, Integer> articles;

	public void setArticles(Map<Article, Integer> articles) {
		this.articles = articles;
	}

	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public CustomerOrder() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerOrder(String orderDate, String dispatchDate, Customer customer, Map<Article, Integer> articles) {

		this.orderDate = orderDate;
		this.dispatchDate = dispatchDate;
		this.customer = customer;
		this.articles = articles;
	}

	public int getOrderNr() {
		return orderNr;
	}

	public String getDispatchDate() {
		return dispatchDate;
	}
	
	public String getorderDate() {
		return orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer= customer;
	}
	
	public void setOrderNr(int orderNr) {
		this.orderNr = orderNr;
	}
	
	/**
	 * @author Peter
	 * @return
	 */
	private double getSubTotalNoDiscount() {
		double total = 0;
		for(Article a : articles.keySet()) {
			total += a.getPrice() * articles.get(a);
		}
		return total;
	}
	
	/**
	 * @author Peter
	 * @return
	 */
	public double getTotal() {
		return getSubTotalNoDiscount() - getTotalDiscount();
	}
	
	/**
	 * @author Peter
	 * @return
	 */
	public double getTotalDiscount() {
		return getSubTotalNoDiscount() * customer.getDiscount();
	}

	public Map<Article, Integer> getArticles() {
		return articles;
	}

	/**
	 *@author Elske
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double stnd = getSubTotalNoDiscount();
		double td = getTotalDiscount();
		double total = getTotal();
		
		sb.append(customer.toString());
		sb.append("\nOrderNumber: " + orderNr);
		sb.append("\nOrderdate: " + orderDate);
		sb.append("\nDispatchdate: " + dispatchDate);
		sb.append("\nNumber / Article Id\n");
		
		Iterator it = articles.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry orderArt = (Map.Entry)it.next();
			sb.append(orderArt.getKey() + " / " + orderArt.getValue() + "\n");
		}
		sb.append("\nSubtotal: " + stnd);
		sb.append("\nCustomer discount: " + td);
		sb.append("\nTotal in SEK: " + total);
		String customerOrder = sb.toString();
		return customerOrder;
		
	}

	
}
