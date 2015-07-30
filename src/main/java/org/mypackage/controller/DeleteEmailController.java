/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.dal.RepositoryFactory;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailController implements IDeleteEmailController {

    private ContactRepository contactRepository;

    public DeleteEmailController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }

    public DeleteEmailController(RepositoryFactory repositoryFactory) {
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
