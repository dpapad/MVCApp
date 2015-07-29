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
public class NewContactController implements INewContactController {
    
    private ContactRepository contactRepository;
    
    public NewContactController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public NewContactController(RepositoryFactory repositoryFactory){
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    @Override
    public int addNewContact(String fullName, String nickname, String notes){
        int contactId = 0;
        
        Contact contact = new Contact();
        contact.setFullName(fullName);
        contact.setNickname(nickname);
        contact.setNotes(notes);
        
        try {
            contactId = this.contactRepository.addContact(contact);
        } catch (Exception e) {
        }
        
        return contactId;
    }
}
