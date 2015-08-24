/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dev-dp
 */

public class DeleteContactControllerImpl implements DeleteContactController {

    @Autowired
    private ContactRepository contactRepository;
    public DeleteContactControllerImpl(){ 
        
    }
    @Autowired
    public DeleteContactControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    

    @Override
    public void deleteContact(String contactId) throws MalformedIdentifierException, DalException {
        int id;
        
        try {
            id = Integer.parseInt(contactId);
        }
        catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(contactId, ex);
        }
        
        this.contactRepository.deleteContactById(id);
        
    }
}
