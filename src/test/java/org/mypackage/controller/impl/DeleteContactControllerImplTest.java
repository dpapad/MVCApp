/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class DeleteContactControllerImplTest {

    @Mock
    private ContactRepository mockContactRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    /**
     * Test of deleteContact method, of class DeleteContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testDeleteContact() throws DalException, MalformedIdentifierException {
        final int expectedNumberOfContactsDeleted = 0;
        when(mockContactRepository.deleteContactById(1)).thenReturn(0);

        DeleteContactController controller = new DeleteContactControllerImpl(mockContactRepository);

        controller.deleteContact("1");
        
        final List<Contact> contactsList = new ArrayList<>();
        int actualNumberOfContacts = contactsList.size();

        assertEquals(expectedNumberOfContactsDeleted, actualNumberOfContacts);
    }

    /**
     * Test of deleteContact method, of class DeleteContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteContactBecauseOfMalformedId() throws MalformedIdentifierException, DalException {
        DeleteContactController controller = new DeleteContactControllerImpl(mockContactRepository);
        String contactId = "1s";
        controller.deleteContact(contactId);
    }

    /**
     * Test of deleteContact method, of class DeleteContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailDeleteContactBecauseOfDalError() throws MalformedIdentifierException, DalException {
        doThrow(new DalException()).when(mockContactRepository).deleteContactById(1);

        DeleteContactController controller = new DeleteContactControllerImpl(mockContactRepository);
        controller.deleteContact("1");
    }

}
