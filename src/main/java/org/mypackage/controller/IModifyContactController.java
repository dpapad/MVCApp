/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public interface IModifyContactController {

    Contact modifyContact(String contactId, String fullname, String nickname, String notes) throws DalException;

    Contact retrieveContact(String contactId) throws DalException;

}
