package domain;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = -6937327123991374742L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;//Or string name?
	
	private String surname;
	private String lastName;
	private String address;
	
	private double discount;//??
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String surname, String lastName, String address, double discount) {
		this.surname = surname;
		this.lastName = lastName;
		this.address = address;
		this.discount = discount;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
