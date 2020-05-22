package domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="tblCustomer")
public class Customer implements Serializable {
	private static final long serialVersionUID = -6937327123991374742L;

	@Id
	@XmlAttribute(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cnr")
	private int cnr;//Or string name?
	
	private String firstName;
	private String lastName;
	private String address;
	private String zipCode;
	private String city;
	
	private double discount;//??
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String firstName, String lastName, String address, String zipCode, String city, double discount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.discount = discount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}	
	
	public int getCnr() {
		return cnr;
	}

	@Override
	public String toString() {
		return "Customer#: " + getCnr() + "\n" + firstName + " " + lastName + "\n" + address
				+ "\n" + zipCode + " " + city;
	}
	
	
	
}
