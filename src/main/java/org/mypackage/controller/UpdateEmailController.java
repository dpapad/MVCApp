/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public interface UpdateEmailController {

    Email updateEmail(String id, String address, String categoryValue, String fContactId) throws MalformedIdentifierException, MalformedCategoryException, DalException, DuplicateEmailException;
    
    Email retrieveEmail(String id) throws DalException, MalformedIdentifierException;
}
