/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author dev-dp
 */
@Component
public class DeleteEmailControllerImpl implements DeleteEmailController {

    @Autowired
    private ContactRepository contactRepository;

    public DeleteEmailControllerImpl() {
        //this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }
    @Autowired
    public DeleteEmailControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void deleteEmail(String eId) throws MalformedIdentifierException, DalException {
        int emailId;
        try {
            emailId = Integer.parseInt(eId);
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(eId, ex);
        }
        this.contactRepository.deleteEmailById(emailId);

    }

}
