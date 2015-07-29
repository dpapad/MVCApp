/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class ModifyContactController implements IModifyContactController {
    
    private ContactRepository contactRepository;
    
    public ModifyContactController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public ModifyContactController(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    @Override
    public Contact modifyContact(String contactId, String fullname, String nickname, String notes) {
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(contactId));
        contact.setFullName(fullname);
        contact.setNickname(nickname);
        contact.setNotes(notes);
        
        try {
            this.contactRepository.updateContact(contact);
        } catch (Exception e) {
        }
        return contact;
    }
    
    @Override
    public Contact retrieveContact(String contactId) {
        
        Contact contact = new Contact();
        int id = Integer.parseInt(contactId);
        try {
            contact = contactRepository.getContactById(id);
            
        } catch (Exception e) {
        }
        return contact;
    }
    
}
