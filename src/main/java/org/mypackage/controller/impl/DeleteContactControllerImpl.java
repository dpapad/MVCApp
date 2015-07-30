/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;

/**
 *
 * @author dev-dp
 */
public class DeleteContactControllerImpl implements DeleteContactController {

    private ContactRepository contactRepository;

    public DeleteContactControllerImpl() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public DeleteContactControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void deleteContact(String contactId) throws NumberFormatException, DalException {
        int id = Integer.parseInt(contactId);
        this.contactRepository.deleteContactById(id);

    }
}