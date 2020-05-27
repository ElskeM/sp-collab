package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
	@CollectionTable(name = "tblOrderRows", joinColumns = {
			@JoinColumn(name = "orderNr", referencedColumnName = "orderNr") })
	@Column(name = "antal")
	@MapKeyColumn(name = "artNr")
	@XmlElement
	private Map<String, Integer> articles;

	@XmlElement
	private double total;
	
	@Transient
	private double totalDiscount;
	
	@Transient
	@XmlTransient //Den här annotationen gör så att fältet döljs i XML-representationen av objektet
	private double totalWithDiscount;
	
	
	/**
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	public void setArticles(Map<String, Integer> articles) {
		this.articles = articles;
	}

	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public CustomerOrder() {
		// Tom konstruktorn för JPA
	}

	public CustomerOrder(String orderDate, String dispatchDate, Customer customer, Map<String, Integer> articles) {
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
	public double getTotal() {
		return this.total;
	}
	
	public double getTotalWithDiscount() {
		return getTotal() - getTotalDiscount();
	}
	
	/**
	 * @author Peter
	 * @return
	 */
	public double getTotalDiscount() {
		return getTotal() * customer.getDiscount();
	}

	public Map<String, Integer> getArticles() {
		return articles;
	}

	/**
	 *@author Elske
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double stnd = getTotal();
		double td = getTotalDiscount();
		double total = getTotalWithDiscount();
		
		sb.append(customer.toString());
		sb.append("\nOrderNumber: " + orderNr);
		sb.append("\nOrderdate: " + orderDate);
		sb.append("\nDispatchdate: " + dispatchDate);
		sb.append("\nNumber / Article Id\n");
		
		Iterator<Entry<String, Integer>> it = articles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> orderArt = (Map.Entry<String, Integer>) it.next();
			sb.append(orderArt.getKey() + " / " + orderArt.getValue() + "\n");
		}
		sb.append("\nSubtotal: " + stnd);
		sb.append("\nCustomer discount: " + td);
		sb.append("\nTotal in SEK: " + total + "\n");
		String customerOrder = sb.toString();
		
		return customerOrder;	
	}
	
}
