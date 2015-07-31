/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailControllerImpl implements DeleteEmailController {

    private ContactRepository contactRepository;

    public DeleteEmailControllerImpl() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public DeleteEmailControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public int deleteEmail(String eId, String cId) throws MalformedIdentifierException, DalException {
        int emailId = Integer.parseInt(eId);
        this.contactRepository.deleteEmailById(emailId);

        int contactId = Integer.parseInt(cId);
        return contactId;
    }

}
