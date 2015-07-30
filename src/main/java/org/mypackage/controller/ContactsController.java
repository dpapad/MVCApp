/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import java.util.List;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class ContactsController implements IContactsController {

    private ContactRepository contactRepository;

    public ContactsController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }

    public ContactsController(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }

    @Override
    public List<Contact> retrieveAllContacts() throws DalException {
        List<Contact> list = null;

        list = this.contactRepository.getAllContacts();

        return list;
    }

    @Override
    public Contact getContact(String id) throws NumberFormatException, DalException {
        Contact contact = null;

        int contactId = Integer.parseInt(id);
        contact = this.contactRepository.getContactById(contactId);

        return contact;
    }

    @Override
    public List<Email> retrieveAllEmails(String id) throws NumberFormatException, DalException {
        List<Email> list = null;

        int contactId = Integer.parseInt(id);

        list = this.contactRepository.getAllEmailsByContactId(contactId);

        return list;
    }
}
