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
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.dal.FakeContactRepository;

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
     */
    @Test
    public void testDeleteContact() throws Exception {
        System.out.println("DeleteContact");
        ContactRepository fakeRepository = new FakeContactRepository();
        DeleteContactController controller = new DeleteContactControllerImpl(fakeRepository);

        String contactId = "1";

        int affectedRows = controller.deleteContact(contactId);

        assertEquals(6, affectedRows);

    }
    
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteBecauseOfMalformedId() throws MalformedIdentifierException, DalException {
        ContactRepository fakeRepository = new FakeContactRepository();
        DeleteContactController controller = new DeleteContactControllerImpl(fakeRepository);
        String contactId = "1s";
        controller.deleteContact(contactId);
    }

}
