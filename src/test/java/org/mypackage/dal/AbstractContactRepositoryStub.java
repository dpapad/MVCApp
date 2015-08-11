package org.mypackage.dal;

import java.util.List;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class AbstractContactRepositoryStub implements ContactRepository {

    @Override
    public int addContact(Contact c) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deleteContactById(int i) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Contact updateContact(Contact c) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Contact getContactById(int id) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getAllContacts() throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addEmail(Email e) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Email> getAllEmailsByContactId(int id) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deleteEmailById(int id) throws DalException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkIfEmailExists(Email e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
