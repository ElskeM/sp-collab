package service;

import javax.ejb.ApplicationException;

/**
 * @author elske
 *
 */
@ApplicationException(rollback=true)
public class ServiceUnavailableException extends Exception {

}
