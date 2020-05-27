package dao;

import javax.ejb.ApplicationException;

/**
 * Exception for when trying to delete an entity that is part of other entitys
 * @author simon
 *
 */
@ApplicationException(rollback=true)
public class ForbiddenDeleteException extends Exception {

}
