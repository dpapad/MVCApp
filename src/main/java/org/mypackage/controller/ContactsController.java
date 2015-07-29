/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import java.text.ParseException;
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
    public List<Contact> retrieveAllContacts() {
        List<Contact> list = null;
        try {
            list = this.contactRepository.getAllContacts();
        } catch (Exception e) {
        }
        
        return list;
    }
    
    @Override
    public Contact getContact(String id) {
        Contact contact = null;
        try {
            int contactId = Integer.parseInt(id);
            contact = this.contactRepository.getContactById(contactId);
            
        } 
        catch (NumberFormatException e) {
            
        }
        catch (DalException ex) {
            
        }
        
        return contact;
    }
    
    @Override
    public List<Email> retrieveAllEmails(String id) {
        List<Email> list = null;
        try {
            int contactId = Integer.parseInt(id);
            try {
                list = this.contactRepository.getAllEmailsByContactId(contactId);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        
        return list;
    }
}
