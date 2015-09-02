/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.UpdateEmailController;
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
public class UpdateEmailControllerImpl implements UpdateEmailController {

    @Autowired
    private ContactRepository contactRepository;
    private Byte category;

    public UpdateEmailControllerImpl() {
    }

    @Autowired
    public UpdateEmailControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Email updateEmail(String id, String address, String categoryValue, String fContactId) throws MalformedIdentifierException, MalformedCategoryException, DalException, DuplicateEmailException {
        Email email = new Email();
        try {
            email.setId(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(id, ex);
        }
        email.setAddress(address);

        try {
            category = Byte.parseByte(categoryValue);
            email.setCategory(Email.Category.forValue(category));
        } catch (NumberFormatException ex) {
            throw new MalformedCategoryException(categoryValue, ex);
        }

        try {
            email.setfContactId(Integer.parseInt(fContactId));
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(fContactId, ex);
        }

        if (!(this.contactRepository.checkIfEmailExists(email))) {
            this.contactRepository.updateEmailById(email);
        } else {
            throw new DuplicateEmailException(email.getAddress());
        }

        return email;
    }

    @Override
    public Email retrieveEmail(String emailId) throws DalException, MalformedIdentifierException {

        Email email = new Email();
        int id;

        try {
            id = Integer.parseInt(emailId);
            email = contactRepository.getEmailById(id);
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(emailId, ex);
        }
        return email;
    }

}
