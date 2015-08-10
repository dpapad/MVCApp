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
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.FakeContactRepository;
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
     */
    @Test
    public void testModifyContact() throws Exception {

        ContactRepository fakeRepository = new FakeContactRepository();
        ModifyContactController controller = new ModifyContactControllerImpl(fakeRepository);

        String contactId = "1";
        String fullname = "TestModifyContact";
        String nickname = "test1";
        String notes = "this is a test";

        Contact contact = controller.modifyContact(contactId, fullname, nickname, notes);

        assertEquals(fullname, contact.getFullName());
    }

    /**
     * Test of retrieveContact method, of class ModifyContactControllerImpl.
     */
    @Test
    public void testRetrieveContact() throws Exception {

        ContactRepository fakeRepository = new FakeContactRepository();
        ModifyContactController controller = new ModifyContactControllerImpl(fakeRepository);

        String contactId = "1";

        Contact result = controller.retrieveContact(contactId);

        assertEquals("Joe Doe", result.getFullName());

    }

}
