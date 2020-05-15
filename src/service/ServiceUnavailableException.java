package service;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ServiceUnavailableException extends Exception {

}
