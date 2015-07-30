/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller;

/**
 *
 * @author dev-dp
 */
public interface ControllerFactory {
    
    ContactsController createContactsController();
    
    DeleteContactController createDeleteContactController();
    
    DeleteEmailController createDeleteEmailController();
    
    ModifyContactController createModifyContactController();
    
    NewContactController createNewContactController();
    
    NewEmailController createNewEmailController();
}
