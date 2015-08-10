/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.view;

import org.mypackage.controller.ContactsController;
import org.mypackage.controller.ControllerFactory;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.controller.NewContactController;
import org.mypackage.controller.NewEmailController;
import org.mypackage.controller.impl.ContactsControllerImpl;
import org.mypackage.controller.impl.DeleteContactControllerImpl;
import org.mypackage.controller.impl.DeleteEmailControllerImpl;
import org.mypackage.controller.impl.ModifyContactControllerImpl;
import org.mypackage.controller.impl.NewContactControllerImpl;
import org.mypackage.controller.impl.NewEmailControllerImpl;

/**
 *
 * @author dev-dp
 */
public class ServletControllerFactory implements ControllerFactory {

    @Override
    public ContactsController createContactsController() {
        return new ContactsControllerImpl();
    }

    @Override
    public DeleteContactController createDeleteContactController() {
        return new DeleteContactControllerImpl();
    }

    @Override
    public DeleteEmailController createDeleteEmailController() {
        return new DeleteEmailControllerImpl();
    }

    @Override
    public ModifyContactController createModifyContactController() {
        return new ModifyContactControllerImpl();
    }

    @Override
    public NewContactController createNewContactController() {
        return new NewContactControllerImpl();
    }

    @Override
    public NewEmailController createNewEmailController() {
        return new NewEmailControllerImpl();
    }

}
