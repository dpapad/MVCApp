/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.model.Email;
/**
 *
 * @author dev-dp
 */
public class NewEmailController {
    
    private ContactRepository contactRepository;
    
    public NewEmailController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public NewEmailController(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    public int addNewEmail(String address, String categoryValue, String contactId){
        Email email = new Email();
        
        // check logic for address format to be added
        
        email.setAddress(address);
        try {
            email.setCategory(Email.Category.forValue(Byte.parseByte(categoryValue)));
        } 
        catch (Exception e) {
        }
        try { 
            email.setfContactId(Integer.parseInt(contactId));
        }
        catch (Exception e) {
        }
        
        
        int passedContactId = -10;
        try {
            if(!(this.contactRepository.checkIfEmailExists(email))) {
                this.contactRepository.addEmail(email);
                passedContactId = email.getfContactId();
            }
            else {
                passedContactId = -10;
            }
        } catch (Exception e) {
        }
        return passedContactId;
    }
}
