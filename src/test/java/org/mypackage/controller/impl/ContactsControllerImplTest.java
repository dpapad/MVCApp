/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.application.errors.ResourceNotFoundException;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactsControllerImplTest {

    @Mock
    private ContactRepository mockContactRepository;

    //private ContactsController controller = new ContactsControllerImpl(contactRepositoryMock);
    /**
     * Test of retrieveAllContacts method, of class ContactsControllerImpl.
     *
     * @throws DalException
     */
    @Test
    public void testRetrieveAllContacts() throws DalException {

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);

        final List<Contact> expectedContacts = new ArrayList();
        expectedContacts.add(new Contact(1, "John Doe", null, null));

        when(mockContactRepository.getAllContacts()).thenReturn(expectedContacts);

        List<Contact> actualContacts = controller.retrieveAllContacts();
        assertEquals(expectedContacts, actualContacts);
    }


    @Test(expected = DalException.class)
    public void testFailToRetrieveAllContactsBecauseOfDalError() throws DalException {
        when(mockContactRepository.getAllContacts()).thenThrow(new DalException());

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);
        controller.retrieveAllContacts();
    }

    /**
     * Test of getContact method, of class ContactsControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws ResourceNotFoundException
     */
    @Test
    public void testGetContact() throws MalformedIdentifierException, DalException, ResourceNotFoundException {

        final Contact expectedContact = new Contact(1, "John Doe", null, null);

        when(mockContactRepository.getContactById(expectedContact.getId())).thenReturn(expectedContact);

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);

        Contact actualContact = controller.getContact(Integer.toString(expectedContact.getId()));
        assertEquals(expectedContact, actualContact);
    }

    @Test(expected = MalformedIdentifierException.class)
    public void testFailToGetContactBecauseOfMalformedId() throws MalformedIdentifierException, DalException, ResourceNotFoundException {
        ContactsController controller = new ContactsControllerImpl(mockContactRepository);
        final String id = "1s";
        controller.getContact(id);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFailToGetContactBecauceItDoesNotExist() throws MalformedIdentifierException, DalException, ResourceNotFoundException {

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);
        controller.getContact("1");
    }

    /**
     * Test of retrieveAllEmails method, of class ContactsControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testRetrieveAllEmails() throws DalException, MalformedIdentifierException {
        final List<Email> expectedEmails = new ArrayList<>();
        expectedEmails.add(new Email(1, null, Email.Category.PERSONAL, 1));
        when(mockContactRepository.getAllEmailsByContactId(1)).thenReturn(expectedEmails);

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);

        List<Email> actualEmails = controller.retrieveAllEmails(Integer.toString(expectedEmails.get(0).getfContactId()));

        assertEquals(expectedEmails, actualEmails);
    }

    /**
     * Test of retrieveAllEmails method, of class ContactsControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToRetreiveAllEmailsBecauseOfMalformedContactId() throws MalformedIdentifierException, DalException {

        ContactsController controller = new ContactsControllerImpl(mockContactRepository);

        controller.retrieveAllEmails("1a");
    }

    /**
     * Test of retrieveAllEmails method, of class ContactsControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailToRetreiveAllEmailsBecauseOfDalError() throws DalException, MalformedIdentifierException {
        when(mockContactRepository.getAllEmailsByContactId(1)).thenThrow(new DalException());
        ContactsController controller = new ContactsControllerImpl(mockContactRepository);
        controller.retrieveAllEmails("1");
    }

}
