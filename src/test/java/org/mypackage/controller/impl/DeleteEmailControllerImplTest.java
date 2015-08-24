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
import org.junit.runner.RunWith;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.AbstractContactRepositoryStub;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author dev-dp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class DeleteEmailControllerImplTest {

    @Autowired
    private AbstractContactRepositoryStub contactRepositoryStub;

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

        int expectedNumberOfEmails = 0;
        final List<Email> emailList = new ArrayList<>();

        contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public void deleteEmailById(int id) throws DalException {
                emailList.remove(0);
            }
        };

        DeleteEmailController controller = new DeleteEmailControllerImpl(contactRepositoryStub);

        emailList.add(new Email(1, "test@mail.asdf", Email.Category.PERSONAL, 1));

        controller.deleteEmail("1");
        int actualNumberOfEmails = emailList.size();

        assertEquals(expectedNumberOfEmails, actualNumberOfEmails);
    }

    /**
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        contactRepositoryStub = new AbstractContactRepositoryStub() {
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
        contactRepositoryStub = new AbstractContactRepositoryStub() {

            @Override
            public void deleteEmailById(int id) throws DalException {
                throw new DalException();
            }
        };

        DeleteEmailController controller = new DeleteEmailControllerImpl(contactRepositoryStub);
        controller.deleteEmail("1");
    }

}
