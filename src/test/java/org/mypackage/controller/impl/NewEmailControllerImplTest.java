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
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.FakeContactsRepository;
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
     */
    @Test
    public void testAddNewEmail() throws Exception {
        System.out.println("addNewEmail");
        
        ContactRepository fakeRepository = new FakeContactsRepository();
        NewEmailController controller = new NewEmailControllerImpl(fakeRepository);
        
        
        String address = "testemail@email.test";
        String categoryValue = "1";
        String contactId = "1";
        
        
        
       
        int result = controller.addNewEmail(address, categoryValue, contactId);
        assertEquals(1, result);
        
    }
    
}
