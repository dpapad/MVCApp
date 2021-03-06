package org.mypackage.dal;

import java.util.List;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
public interface ContactRepository {

    int addContact(Contact c) throws DalException;

    int deleteContactById(int i) throws DalException;

    int updateContact(Contact c) throws DalException;

    Contact getContactById(int id) throws DalException;

    List<Contact> getAllContacts() throws DalException;

    int addEmail(Email e) throws DalException;
    
    Email getEmailById(int id) throws DalException;
    
    Email updateEmailById(Email e) throws DalException;

    List<Email> getAllEmailsByContactId(int id) throws DalException;

    int deleteEmailById(int id) throws DalException;

    boolean checkIfEmailExists(Email e) throws DalException;
}
