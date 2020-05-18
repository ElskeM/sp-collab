package domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "tblArticle")
public class Article implements Serializable {
	private static final long serialVersionUID = 27799321788954129L;
	
	@SequenceGenerator(name = "ART_SEQ", initialValue = 10000)
	
	@Id
	@XmlAttribute
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ART_SEQ")
	@Column(name = "artNr")
	private int artNr;
	public String name;
	public String  description;
	public int stock; //Perhaps better with a warehouse entity that holds the stock. Stock does not "belong" to article
	private double price;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article(String name, String desc, int stock, double price) {
		this.name=name;
		description=desc;
		this.stock=stock;
		this.price=price;
	}

	public int getArtNr() {
		return artNr;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}
