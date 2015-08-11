/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.dal.FakeContactRepository;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class ContactsControllerImplTest {

    public ContactsControllerImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of retrieveAllContacts method, of class ContactsControllerImpl.
     * 
     * @throws DalException
     */
    @Test
    public void testRetrieveAllContacts() throws DalException {
        final List<Contact> expectedContacts = new ArrayList();
        expectedContacts.add(new Contact(1, "John Doe", null, null));
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public List<Contact> getAllContacts() throws DalException {
                return expectedContacts;
            }
            
        };
        ContactsController controller = new ContactsControllerImpl(contactRepositoryStub);
        List<Contact> actualContacts = controller.retrieveAllContacts();
        assertEquals(expectedContacts, actualContacts);
    }
    
    @Test(expected = DalException.class)
    public void testFailToRetrieveAllContactsBecauseOfDalError() throws DalException {
        ContactRepository contactRepositoryStub = new FakeContactRepository() {

            @Override
            public List<Contact> getAllContacts() throws DalException {
                throw new DalException();
            }
            
        };
        ContactsController controller = new ContactsControllerImpl(contactRepositoryStub);
        controller.retrieveAllContacts();
    }

    /**
     * Test of getContact method, of class ContactsControllerImpl.
     * 
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testGetContact() throws MalformedIdentifierException, DalException {
        final Contact expectedContact = new Contact(1, "John Doe", null, null);
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public Contact getContactById(int id) throws DalException {
                return expectedContact;
            }
            
        };
        ContactsController controller = new ContactsControllerImpl(contactRepositoryStub);
        Contact actualContact = controller.getContact(Integer.toString(expectedContact.getId()));
        assertEquals(expectedContact, actualContact);
    }
    
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToGetContactBecauseOfMalformedId() throws MalformedIdentifierException, DalException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() { };
        ContactsController controller = new ContactsControllerImpl(contactRepositoryStub);
        final String id = "1s";
        controller.getContact(id);
    }

    /**
     * Test of retrieveAllEmails method, of class ContactsControllerImpl.
     */
    @Test
    public void testRetrieveAllEmails() throws Exception {
        ContactRepository fakeRepository = new FakeContactRepository();

        ContactsController controller = new ContactsControllerImpl(fakeRepository);

        int numberOfEmails = controller.retrieveAllEmails("1").size();

        assertEquals(5, numberOfEmails);
    }

}
