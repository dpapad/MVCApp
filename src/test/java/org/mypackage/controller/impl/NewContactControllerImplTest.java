/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author dev-dp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class NewContactControllerImplTest {
    
    @Autowired
    private AbstractContactRepositoryStub contactRepositoryStub;

    public NewContactControllerImplTest() {
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
     * Test of addNewContact method, of class NewContactControllerImpl.
     * 
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testAddNewContact() throws DalException, MalformedIdentifierException {
        Contact contact = new Contact(1, "John", "Doe", "asdf");
        final int expectedContactId = contact.getId();
        contactRepositoryStub = new AbstractContactRepositoryStub(){

            @Override
            public int addContact(Contact c) throws DalException {
                return expectedContactId;
            }            
        };
        
        NewContactController controller = new NewContactControllerImpl(contactRepositoryStub);
        
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
    public void testFailToAddNewContactBecauseOfDalError() throws DalException{
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub(){

            @Override
            public int addContact(Contact c) throws DalException {
                throw new DalException();
            }        
        };
        NewContactController controller = new NewContactControllerImpl(contactRepositoryStub);
        controller.addNewContact("Joe", "Doe", null);
    }

}
