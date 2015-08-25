/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import java.util.List;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.application.errors.ResourceNotFoundException;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dev-dp
 */
@Service
public class ContactsControllerImpl implements ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    public ContactRepository getContactRepository() {
        return contactRepository;
    }

    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactsControllerImpl() {

    }

    @Autowired
    public ContactsControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> retrieveAllContacts() throws DalException {
        List<Contact> list = null;
        list = this.contactRepository.getAllContacts();
        return list;
    }

    @Override
    public Contact getContact(String id) throws MalformedIdentifierException, DalException, ResourceNotFoundException {
        Contact contact = null;
        int contactId;

        try {
            contactId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(id, ex);
        }

        contact = this.contactRepository.getContactById(contactId);
        if (contact == null) {
            throw new ResourceNotFoundException((Object) id);
        }
        return contact;
    }

    @Override
    public List<Email> retrieveAllEmails(String id) throws MalformedIdentifierException, DalException {
        List<Email> list = null;
        int contactId;

        try {
            contactId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(id, ex);
        }
        list = this.contactRepository.getAllEmailsByContactId(contactId);

        return list;
    }
}
