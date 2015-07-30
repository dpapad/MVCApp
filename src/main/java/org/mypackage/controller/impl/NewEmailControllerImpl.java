/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class NewEmailControllerImpl implements NewEmailController {

    private ContactRepository contactRepository;

    public NewEmailControllerImpl() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public NewEmailControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public int addNewEmail(String address, String categoryValue, String contactId) throws NumberFormatException, DalException {
        Email email = new Email();

        // check logic for address format to be added
        email.setAddress(address);
        email.setCategory(Email.Category.forValue(Byte.parseByte(categoryValue)));
        email.setfContactId(Integer.parseInt(contactId));

        int passedContactId;

        if (!(this.contactRepository.checkIfEmailExists(email))) {
            this.contactRepository.addEmail(email);
            passedContactId = email.getfContactId();
        } else {
            passedContactId = -10;
        }

        return passedContactId;
    }
}
