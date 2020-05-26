package rest;

enum MESSAGE_TYPE {
	ArticleOutOfStock("Article out of stock"),
	ServerError("Server Error");

	private String error;
	
	MESSAGE_TYPE(String string) {
		this.error = string;
	}
	
	public String getError() {
		return error;
	}
}

public class ErrorMessage {
	
	private String error;
	private String message;
	

	public ErrorMessage(String message, MESSAGE_TYPE type) {
		this.message = message;
		this.error = type.getError();
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

}
