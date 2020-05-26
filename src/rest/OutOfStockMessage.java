package rest;

/**
 * @author Peter
 *
 */
public class OutOfStockMessage {
	
	private String error = "Item is out of stock";
	private String message;
	

	public OutOfStockMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

}
