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
public class DeleteContactController implements IDeleteContactController {

    private ContactRepository contactRepository;

    public DeleteContactController() {
        this(ApplicationDependencies.REPOSITORY_FACTORY);
    }

    public DeleteContactController(RepositoryFactory repositoryFactory) {
        this.contactRepository = repositoryFactory.createContactRepository();
    }

    @Override
    public void deleteContact(String contactId) throws NumberFormatException, DalException {
        int id = Integer.parseInt(contactId);
        this.contactRepository.deleteContactById(id);

    }
}
