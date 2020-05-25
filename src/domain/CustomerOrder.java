package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
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
import javax.xml.bind.annotation.XmlTransient;

import jdk.nashorn.internal.ir.annotations.Ignore;
import util.StringToIntegerConverter;

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
//	@Embedded
//	@Convert(converter = StringToIntegerConverter.class,
//	attributeName = "city")
	private Customer customer;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tblOrderRows", joinColumns = {
			@JoinColumn(name = "orderNr", referencedColumnName = "orderNr") })
	@Column(name = "antal")
	//@MapKeyJoinColumn(name = "artNr")
	@MapKeyColumn(name = "artNr")
	@XmlElement
//	@Convert(converter = StringToIntegerConverter.class,
//			attributeName = "key")
	private Map<String, Integer> articles;

	@XmlElement
	private double total;
	
	@Transient
	private double totalDiscount;
	
	@Transient
	@XmlTransient //Den här annotationen gör så att fältet döljs i XML-representationen av objektet
	private double totalWithDiscount;
	
//	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
//		System.out.println("TESTDESERIALIZER");
//		this.orderNr = 2;
//		this.customer = new Customer("John","Petrucci","Gibsongatan 12", "425 71", "Göteborg", 0.5);
//		this.customer.setCustomerNr(116);
//		this.dispatchDate = "2006-01-01";
//		this.orderDate = "2006-01-01";
//		Map<Article, Integer> articles = new HashMap<>();
//		Article a1 = new Article("Skruv", "Skruv",100, 1);
//		a1.setArtNr(10005);
//		articles.put(a1, 5);
//		this.articles = articles;
//		in.close();
//	}

	
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

		// TODO Auto-generated constructor stub
	}

	/**
	 * This constructor remains here for the testing dao
	 * 
	 * @param ordD1
	 * @param dispD1
	 * @param customer
	 * @param or1
	 */
	@Deprecated
	public CustomerOrder(String ordD1, String dispD1, Customer customer, HashMap<String, Integer> or1) {

		this.orderDate = sdf.format(ordD1);
		this.dispatchDate = sdf.format(dispD1);
		this.customer = customer;
		this.articles = or1;
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
		
		Iterator it = articles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry orderArt = (Map.Entry) it.next();
			sb.append(orderArt.getKey() + " / " + orderArt.getValue() + "\n");
		}
		sb.append("\nSubtotal: " + stnd);
		sb.append("\nCustomer discount: " + td);
		sb.append("\nTotal in SEK: " + total + "\n");
		String customerOrder = sb.toString();
		return customerOrder;
		
	}

	
}
