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
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class NewContactControllerImplTest {

    @Mock
    ContactRepository mockContactRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }

    /**
     * Test of addNewContact method, of class NewContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testAddNewContact() throws DalException, MalformedIdentifierException {
        Contact contact = new Contact(1, "John", "Doe", "asdf");
        final int expectedContactId = 0;

        when(mockContactRepository.addContact(contact)).thenReturn(0);

        NewContactController controller = new NewContactControllerImpl(mockContactRepository);

        int actualContactId = controller.addNewContact("Test", "Contact", "test for NewContactController");
        

        assertEquals(expectedContactId, actualContactId);
    }

    /**
     * Test of addNewContact method, of class NewContactControllerImpl.
     *
     * @throws DalException
     *
     */
    @Test(expected = DalException.class)
    public void testFailToAddNewContactBecauseOfDalError() throws DalException {

        when(mockContactRepository.addContact(any(Contact.class))).thenThrow(new DalException());

        NewContactController controller = new NewContactControllerImpl(mockContactRepository);
        controller.addNewContact("Joe", "Doe", null);
    }

}
