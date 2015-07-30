/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.mypackage.application.ApplicationDependencies;
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class NewContactControllerImpl implements NewContactController {

    private ContactRepository contactRepository;

    public NewContactControllerImpl() {
        this(ApplicationDependencies.REPOSITORY_FACTORY.createContactRepository());
    }

    public NewContactControllerImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public int addNewContact(String fullName, String nickname, String notes) throws DalException {
        int contactId;

        Contact contact = new Contact();
        contact.setFullName(fullName);
        contact.setNickname(nickname);
        contact.setNotes(notes);

        contactId = this.contactRepository.addContact(contact);

        return contactId;
    }
}
