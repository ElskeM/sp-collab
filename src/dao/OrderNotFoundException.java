package dao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class OrderNotFoundException extends Exception {

}
