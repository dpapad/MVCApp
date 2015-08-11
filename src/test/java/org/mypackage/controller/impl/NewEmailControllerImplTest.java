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
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class NewEmailControllerImplTest {

    public NewEmailControllerImplTest() {
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
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testAddNewEmail() throws DalException, MalformedIdentifierException {

        Email email = new Email(1, "test@mail.com", Email.Category.PERSONAL, 1);
        final int expectedContactId = email.getfContactId();
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public int addEmail(Email e) throws DalException {
                return expectedContactId;
            }

            @Override
            public boolean checkIfEmailExists(Email e) {
                return false;
            }

        };
        NewEmailController controller = new NewEmailControllerImpl(contactRepositoryStub);

        int actualContactId = controller.addNewEmail("test@mail.com", "1", "1");
        assertEquals(expectedContactId, actualContactId);

    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToAddNewEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
        };
        NewEmailController controller = new NewEmailControllerImpl(contactRepositoryStub);
        controller.addNewEmail("test@mail.com", "1", "asdf");
    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailToAddNewEmailBecauseOfDalError() throws DalException, MalformedIdentifierException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public int addEmail(Email e) throws DalException {
                throw new DalException();
            }

            @Override
            public boolean checkIfEmailExists(Email e) {
                return false;
            }

        };
        NewEmailController controller = new NewEmailControllerImpl(contactRepositoryStub);
        controller.addNewEmail("test@mail.com", "1", "1");
    }
}
