/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.dal;

import java.util.ArrayList;
import java.util.List;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class FakeContactRepository implements ContactRepository {

    @Override
    public int addContact(Contact c) throws DalException {

        return 10;
    }

    @Override
    public int deleteContactById(int i) throws DalException {
        int affectedRows = 6;

        return affectedRows;
    }

    @Override
    public int updateContact(Contact c) throws DalException {
        int affectedRows = 1;

        return affectedRows;
    }

    @Override
    public Contact getContactById(int id) throws DalException {

        Contact contact = new Contact();
        contact.setId(id);
        contact.setFullName("Joe Doe");
        contact.setNickname("jo");
        contact.setNotes("fake contact");

        return contact;
    }

    @Override
    public List<Contact> getAllContacts() throws DalException {
        Contact contact = new Contact();
        List<Contact> list = new ArrayList();
        contact.setId(1);
        contact.setFullName("Joe Doe");
        list.add(contact);
        contact.setId(2);
        contact.setFullName("Jack Doe");
        list.add(contact);
        contact.setId(3);
        contact.setFullName("Jim Doe");
        list.add(contact);

        return list;
    }

    @Override
    public int addEmail(Email e) throws DalException {
        int contactId = -10;
        if (e != null) {
            contactId = e.getfContactId();
        }

        return contactId;
    }

    @Override
    public List<Email> getAllEmailsByContactId(int id) throws DalException {
        List<Email> list = new ArrayList();
        int emailId;
        Email email = new Email();
        for (int i = 0; i < 5; i++) {
            emailId = i + 1;
            email.setfContactId(id);
            email.setId(emailId);
            list.add(email);
        }
        return list;
    }

    @Override
    public int deleteEmailById(int id) throws DalException {
        List<Email> list = getAllEmailsByContactId(id);

        list.remove(id);

        return list.size();
    }

    /**
     *
     * @param e
     * @return true if email="email@test.com"
     */
    @Override
    public boolean checkIfEmailExists(Email e) {
        boolean result = false;
        if (e.getAddress() == "email@test.com") {
            result = true;
        }
        return result;
    }

}
