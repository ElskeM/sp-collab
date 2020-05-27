package service;

/**
 * @author Pontus
 *
 */
public class ReceiptSendingService {
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param adress
	 * @param zipcode
	 * @param city
	 * @throws ServiceUnavailableException
	 */
	public static void sendReciept(String firstName, String lastName, String adress, String zipcode, String city)
	throws ServiceUnavailableException { 
		double rand = Math.random();
		if(rand < 0) {
			throw new ServiceUnavailableException();
		}
		
		
	}

}
