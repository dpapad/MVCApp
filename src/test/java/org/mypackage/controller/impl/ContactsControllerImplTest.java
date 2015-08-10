/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mypackage.controller.ContactsController;
import org.mypackage.dal.ContactRepository;
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
     */
    @Test
    public void testRetrieveAllContacts() throws Exception {

        ContactRepository fakeRepository = new FakeContactRepository();

        ContactsController controller = new ContactsControllerImpl(fakeRepository);

        int numberOfContacts = controller.retrieveAllContacts().size();

        assertEquals(3, numberOfContacts);
    }

    /**
     * Test of getContact method, of class ContactsControllerImpl.
     */
    @Test
    public void testGetContact() throws Exception {
        ContactRepository fakeRepository = new FakeContactRepository();

        ContactsController controller = new ContactsControllerImpl(fakeRepository);

        String id = "1";

        Contact contact = controller.getContact(id);

        assertEquals("Joe Doe", contact.getFullName());
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
