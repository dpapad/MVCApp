/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author dev-dp
 */
@Component
public class ModifyContactControllerImpl implements ModifyContactController {
    @Autowired
    private ContactRepository contactRepository;

    public ModifyContactControllerImpl() {
        //this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }
    @Autowired
    public ModifyContactControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact modifyContact(String contactId, String fullname, String nickname, String notes) throws DalException, MalformedIdentifierException {
        Contact contact = new Contact();
        try {
            contact.setId(Integer.parseInt(contactId));
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(contactId, ex);
        }
        contact.setFullName(fullname);
        contact.setNickname(nickname);
        contact.setNotes(notes);

        this.contactRepository.updateContact(contact);

        return contact;
    }

    @Override
    public Contact retrieveContact(String contactId) throws DalException, MalformedIdentifierException {

        Contact contact = new Contact();
        int id;

        try {
            id = Integer.parseInt(contactId);
            contact = contactRepository.getContactById(id);
        } catch (NumberFormatException ex) {
            throw new MalformedIdentifierException(contactId, ex);
        }
        return contact;
    }

}
