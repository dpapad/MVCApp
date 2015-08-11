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
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dev-dp
 */
public class ModifyContactControllerImplTest {

    public ModifyContactControllerImplTest() {
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
     * Test of modifyContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testModifyContact() throws DalException, MalformedIdentifierException {

        final Contact expectedContact = new Contact(1, "Joe", "Doe", "asdf");

        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public void updateContact(Contact c) throws DalException {
                //return expectedContact;
            }
        };

        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);

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
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
        };
        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);
        
        controller.modifyContact("asd", null, null, null);
    }

    /**
     * Test of modifyContact method, of class ModifyContactControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailToModifyContactBecauseOfDalError() throws DalException, MalformedIdentifierException{
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub(){

            @Override
            public void updateContact(Contact c) throws DalException {
                throw new DalException();
            }           
        };
        
        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);
        controller.modifyContact("1", null, null, null);
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

        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public Contact getContactById(int id) throws DalException {
                return expectedContact;
            }
        };
        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);

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
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub(){};
        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);
        
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
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub(){

            @Override
            public Contact getContactById(int id) throws DalException {
                throw new DalException();
            }            
        };
        
        ModifyContactController controller = new ModifyContactControllerImpl(contactRepositoryStub);
        controller.retrieveContact("1");
    }

}
