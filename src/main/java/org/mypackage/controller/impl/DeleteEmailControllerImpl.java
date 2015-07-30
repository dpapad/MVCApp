/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.dal.RepositoryFactory;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailControllerImpl implements DeleteEmailController {

    private ContactRepository contactRepository;

    public DeleteEmailControllerImpl() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }

    public DeleteEmailControllerImpl(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }

    @Override
    public int deleteEmail(String eId, String cId) throws NumberFormatException, DalException {
        int emailId = Integer.parseInt(eId);
        this.contactRepository.deleteEmailById(emailId);

        int contactId = Integer.parseInt(cId);
        return contactId;
    }

}
