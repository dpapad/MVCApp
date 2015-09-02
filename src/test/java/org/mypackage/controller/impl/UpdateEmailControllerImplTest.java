/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.UpdateEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class UpdateEmailControllerImplTest {

    @Mock
    ContactRepository mockContactRepository;

    public UpdateEmailControllerImplTest() {
    }

    @Before
    public void setUp() {
        initMocks(this);
    }

    /**
     * Test of updateEmail method, of class UpdateEmailControllerImpl.
     *
     * @throws org.mypackage.dal.DalException
     * @throws org.mypackage.application.errors.MalformedIdentifierException
     * @throws org.mypackage.application.errors.MalformedCategoryException
     * @throws org.mypackage.application.errors.DuplicateEmailException
     */
    @Test
    public void testUpdateEmail() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
        Email expectedUpdatedEmail = new Email(1, "test@address", Email.Category.PERSONAL, 1);
        when(mockContactRepository.updateEmailById(any(Email.class))).thenReturn(expectedUpdatedEmail);

        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);

        Email actualUpdatedEmail = updateEmailController.updateEmail("1", "test@address", "1", "1");

        assertEquals(expectedUpdatedEmail.getAddress(), actualUpdatedEmail.getAddress());
    }

    /**
     * 
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException 
     */
    @Test(expected = DalException.class)
    public void testFailToUpdateEmailBecauseOfDalError() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
        when(mockContactRepository.updateEmailById(any(Email.class))).thenThrow(new DalException());
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);
        updateEmailController.updateEmail("1", "asdf@asdf", "1", "1");
    }

    /**
     * 
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException 
     */
    @Test(expected = MalformedCategoryException.class)
    public void testFailToUpdateEmailBecauseOfMalformedCategoryValue() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
        when(mockContactRepository.updateEmailById(any(Email.class))).thenThrow(MalformedCategoryException.class);
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);
        updateEmailController.updateEmail("1", "asdf@asdf", "12a", "1");
    }

    /**
     * 
     * @throws DalException
     * @throws MalformedIdentifierException
     * @throws MalformedCategoryException
     * @throws DuplicateEmailException 
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToUpdateEmailBecauseOfMalformedIdException() throws DalException, MalformedIdentifierException, MalformedCategoryException, DuplicateEmailException {
        when(mockContactRepository.updateEmailById(any(Email.class))).thenThrow(MalformedIdentifierException.class);
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);
        updateEmailController.updateEmail("1s", "asdf@asdf.com", "1", "asdf");
    }

    /**
     * Test of retrieveEmail method, of class UpdateEmailControllerImpl.
     *
     * @throws org.mypackage.dal.DalException
     * @throws org.mypackage.application.errors.MalformedIdentifierException
     */
    @Test
    public void testRetrieveEmail() throws DalException, MalformedIdentifierException {
        Email expectedRetrievedEmail = new Email(1, "test@address.com", Email.Category.PERSONAL, 1);
        when(mockContactRepository.getEmailById(1)).thenReturn(expectedRetrievedEmail);
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);

        Email actualRetrievedEmail = updateEmailController.retrieveEmail("1");
        assertEquals(expectedRetrievedEmail, actualRetrievedEmail);
    }

    /**
     * 
     * @throws DalException
     * @throws MalformedIdentifierException 
     */
    @Test(expected = DalException.class)
    public void testFailToRetrieveEmailBecauseOfDalError() throws DalException, MalformedIdentifierException {
        when(mockContactRepository.getEmailById(any(Integer.class))).thenThrow(DalException.class);
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);
        updateEmailController.retrieveEmail("1");
    }

    /**
     * 
     * @throws DalException
     * @throws MalformedIdentifierException 
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailToRetrieveEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException {
        when(mockContactRepository.getEmailById(any(Integer.class))).thenThrow(MalformedIdentifierException.class);
        UpdateEmailController updateEmailController = new UpdateEmailControllerImpl(mockContactRepository);
        updateEmailController.retrieveEmail("1s");
    }

}
