package domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Article {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	public String name;
	public String  description;
	public int stock; //Perhaps better with a warehouse entity that holds the stock. Stock does not "belong" to article
	private double price;
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article(String name, String desc, int stock,int price) {
		this.name=name;
		description=desc;
		this.stock=stock;
		this.price=price;
	}

	public int getId() {
		return id;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}
