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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailControllerImplTest {

    public DeleteEmailControllerImplTest() {
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
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testDeleteEmail() throws DalException, MalformedIdentifierException {

        final List<Email> emailsList = new ArrayList<>();
        emailsList.add(new Email(1, null, Email.Category.PERSONAL, 1));
        emailsList.remove(0);
        final int expectedRowsAffected = emailsList.size();

        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public int deleteEmailById(int id) throws DalException {
                return expectedRowsAffected;
            }
        };

        DeleteEmailController controller = new DeleteEmailControllerImpl(contactRepositoryStub);

        int actualRowsAffected = controller.deleteEmail("1");

        assertEquals(expectedRowsAffected, actualRowsAffected);
    }

    /**
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
        };
        DeleteEmailController controller = new DeleteEmailControllerImpl(contactRepositoryStub);
        controller.deleteEmail("1asd");
    }
    
    /**
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = DalException.class)
    public void testFailDeleteEmailBecauseOfDalError() throws DalException, MalformedIdentifierException {
        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub(){

            @Override
            public int deleteEmailById(int id) throws DalException {
                throw new DalException();
            }            
        };
        
        DeleteEmailController controller = new DeleteEmailControllerImpl(contactRepositoryStub);
        controller.deleteEmail("1");
    }

}
