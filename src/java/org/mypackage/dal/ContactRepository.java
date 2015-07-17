/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.dal;

import java.util.List;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
public interface ContactRepository {
    
    void addContact(Contact c) throws DalException;
    
    void deleteContactById(int i) throws DalException;
    
    void updateContact(Contact c) throws DalException;
    
    Contact getContactById(int id) throws DalException;
    
    List<Contact> getAllContacts() throws DalException;
    
    void addEmail(Email e) throws DalException;
    
    List<Email> getAllEmailsByContactId(int id) throws DalException;
    
}
