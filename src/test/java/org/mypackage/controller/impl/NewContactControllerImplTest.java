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
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.FakeContactRepository;

/**
 *
 * @author dev-dp
 */
public class NewContactControllerImplTest {

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
     */
    @Test
    public void testAddNewContact() throws Exception {
        System.out.println("addNewContact");
        ContactRepository fakeRepository = new FakeContactRepository();
        NewContactController controller = new NewContactControllerImpl(fakeRepository);

        String fullName = "TestContact Add";
        String nickname = "addContact";
        String notes = "test for NewContactController";

        int result = controller.addNewContact(fullName, nickname, notes);

        int expectedResult = 10;

        assertEquals(expectedResult, result);

    }

}
