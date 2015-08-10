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
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.FakeContactsRepository;

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
     */
    @Test
    public void testDeleteEmail() throws Exception {
        System.out.println("deleteEmail");
        
        ContactRepository fakeRepository = new FakeContactsRepository();
        
        DeleteEmailController controller = new DeleteEmailControllerImpl(fakeRepository);
        
        String eId = "1";
        String cId="1";
        
        
        int expectedResult = 4;
        int result = controller.deleteEmail(eId, cId);
        assertEquals(expectedResult, result);
        
    }
    
}
