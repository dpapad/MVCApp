/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author dev-dp
 */
@Component
public class NewEmailControllerImpl implements NewEmailController {

    @Autowired
    private ContactRepository contactRepository;

    public NewEmailControllerImpl() {
        //this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    @Autowired
    public NewEmailControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public int addNewEmail(String address, String categoryValue, String contactId)
            throws MalformedIdentifierException, DalException, MalformedCategoryException, DuplicateEmailException {
        Email email = new Email();
        Byte category;
        email.setAddress(address);

        try {
            category = Byte.parseByte(categoryValue);
            email.setCategory(Email.Category.forValue(category));
        } catch (NumberFormatException ex) {
            throw new MalformedCategoryException(categoryValue, ex);
        }

        try {
            email.setfContactId(Integer.parseInt(contactId));
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(contactId, ex);
        }
//        int passedContactId;
        int emailId;
        if (!(this.contactRepository.checkIfEmailExists(email))) {
            emailId = this.contactRepository.addEmail(email);
//            passedContactId = email.getfContactId();
        } else {
            throw new DuplicateEmailException(email.getAddress());
        }
        return emailId;
//        return passedContactId;
    }
}
