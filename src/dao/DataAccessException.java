package dao;

/**
 * To be thrown whenever there is a problem communicating with the database.
 * This exception should result in status code 500 being sent to the client.
 * 
 * @author ptemrz
 *
 */
public class DataAccessException extends Exception {

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
