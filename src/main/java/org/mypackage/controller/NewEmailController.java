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

/**
 *
 * @author dev-dp
 */
public interface NewEmailController {

    int addNewEmail(String address, String categoryValue, String contactId) 
            throws MalformedIdentifierException, DalException, MalformedCategoryException, DuplicateEmailException;

}
