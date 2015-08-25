/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class NewEmailControllerImplTest {

    @Mock
    ContactRepository mockContactRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException
     */
    @Test
    public void testAddNewEmail() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {

        Email email = new Email(1, "test@mail.com", Email.Category.PERSONAL, 1);
        final int expectedContactId = email.getfContactId();
        when(mockContactRepository.addEmail(email)).thenReturn(1);

        NewEmailController controller = new NewEmailControllerImpl(mockContactRepository);

        int actualContactId = controller.addNewEmail("test@mail.com", "1", "1");
        assertEquals(expectedContactId, actualContactId);

    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToAddNewEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {

        NewEmailController controller = new NewEmailControllerImpl(mockContactRepository);
        controller.addNewEmail("test@mail.com", "1", "asdf");
    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException
     */
    @Test(expected = DalException.class)
    public void testFailToAddNewEmailBecauseOfDalError() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
//        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
//
//            @Override
//            public int addEmail(Email e) throws DalException {
//                throw new DalException();
//            }
//
//            @Override
//            public boolean checkIfEmailExists(Email e) {
//                return false;
//            }
//
//        };
        when(mockContactRepository.addEmail(any(Email.class))).thenThrow(new DalException());
        NewEmailController controller = new NewEmailControllerImpl(mockContactRepository);
        controller.addNewEmail("test@mail.com", "1", "1");
    }

    /**
     * Test of addNewEmail method, of class NewEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException
     */
    @Test(expected = MalformedCategoryException.class)
    public void testFailToAddNewEmailBecauseOfMalformedCategory() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {

        NewEmailController controller = new NewEmailControllerImpl(mockContactRepository);

        controller.addNewEmail("test@mail.com", "asdf", "1");
    }

    @Test(expected = DuplicateEmailException.class)
    public void testFailToAddNewEmailBecauseItAlreadyExists() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
//        ContactRepository contactRepositoryStub = new AbstractContactRepositoryStub() {
//
//            @Override
//            public boolean checkIfEmailExists(Email e) {
//                return true;
//            }
//        };
        when(mockContactRepository.checkIfEmailExists(any(Email.class))).thenReturn(true);
        NewEmailController controller = new NewEmailControllerImpl(mockContactRepository);
        controller.addNewEmail("testmail@asdf.com", "1", "1");
    }

}
