/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import java.util.List;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.application.errors.ResourceNotFoundException;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public interface ContactsController {

    Contact getContact(String id) throws MalformedIdentifierException, DalException, ResourceNotFoundException;

    List<Contact> retrieveAllContacts() throws DalException;

    List<Email> retrieveAllEmails(String id) throws MalformedIdentifierException, DalException;

}
