/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class ModifyContactControllerImplTest {

    @Mock
    ContactRepository mockContactRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    /**
     * Test of modifyContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testModifyContact() throws DalException, MalformedIdentifierException {

        final Contact expectedContact = new Contact(1, "Joe", "Doe", "asdf");


        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);

        Contact actualContact = controller.modifyContact("1", "Joe", "Doe", "asdf");

        assertEquals(expectedContact.getId(), actualContact.getId());
        assertEquals(expectedContact.getFullName(), actualContact.getFullName());
        assertEquals(expectedContact.getNickname(), actualContact.getNickname());
        assertEquals(expectedContact.getNotes(), actualContact.getNotes());
    }

    /**
     * Test of modifyContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToModifyContactBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);
        controller.modifyContact("asd", null, null, null);
    }

    /**
     * Test of modifyContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailToModifyContactBecauseOfDalError() throws DalException, MalformedIdentifierException {
        when(mockContactRepository.updateContact(any(Contact.class))).thenThrow(new DalException());

        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);
        controller.modifyContact("1", "John", "Doe", "asdf");
    }

    /**
     * Test of retrieveContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testRetrieveContact() throws DalException, MalformedIdentifierException {
        final Contact expectedContact = new Contact(1, "Joe Doe", "asdf", "asdfasdf");

        when(mockContactRepository.getContactById(1)).thenReturn(expectedContact);

        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);

        Contact actualContact = controller.retrieveContact("1");

        assertEquals(expectedContact, actualContact);
    }

    /**
     * Test of retrieveContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToRetrieveContactBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);

        controller.retrieveContact("asdf");
    }

    /**
     * Test of retrieveContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailToRetrieveContactBecauseOfDalError() throws DalException, MalformedIdentifierException {

        when(mockContactRepository.getContactById(1)).thenThrow(new DalException());

        ModifyContactController controller = new ModifyContactControllerImpl(mockContactRepository);
        controller.retrieveContact("1");
    }

}
