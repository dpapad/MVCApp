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
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class DeleteContactControllerImplTest {

    public DeleteContactControllerImplTest() {
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
     * Test of deleteContact method, of class DeleteContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testDeleteContact() throws DalException, MalformedIdentifierException {
        final int expectedNumberOfContacts = 0;
        final List<Contact> contactsList = new ArrayList<>();
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
            @Override
            public void deleteContactById(int i) throws DalException {
                contactsList.remove(0);
            }
        };

        DeleteContactController controller = new DeleteContactControllerImpl(contactRepositoryStub);
        contactsList.add(new Contact(1, null, null, null));
        
        controller.deleteContact("1");
        int actualNumberOfContacts = contactsList.size();

        assertEquals(expectedNumberOfContacts, actualNumberOfContacts);
    }

    /**
     * Test of deleteContact method, of class DeleteContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteContactBecauseOfMalformedId() throws MalformedIdentifierException, DalException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub();
        DeleteContactController controller = new DeleteContactControllerImpl(contactRepositoryStub);
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
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public void deleteContactById(int i) throws DalException {
                throw new DalException();
            }            
        };
        
        DeleteContactController controller = new DeleteContactControllerImpl(contactRepositoryStub);
        controller.deleteContact("1");
    }

}
