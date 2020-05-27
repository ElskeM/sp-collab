package service;

public class recieptSendingService{
	
	public static void sendReceipt(String firstName, String lastName, String address, String zipcode, String city) 
			throws ServiceUnavailableException {
		
		double test = Math.random();
		if(test < 0.5) {
			throw new ServiceUnavailableException();
		}
		
	}
	

}
