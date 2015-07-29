/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.RepositoryFactory;

/**
 *
 * @author dev-dp
 */
public class DeleteContactController {
    private ContactRepository contactRepository;
    
    public DeleteContactController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }
    
    public DeleteContactController(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }
    
    public void deleteContact(String contactId) {
        try {
            int id = Integer.parseInt(contactId);
            
            try {
                this.contactRepository.deleteContactById(id);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }
}
