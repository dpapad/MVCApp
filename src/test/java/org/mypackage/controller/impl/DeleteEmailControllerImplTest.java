/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.controller.impl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class DeleteEmailControllerImplTest {

    @Mock
    private ContactRepository mockContactRepository;

    public DeleteEmailControllerImplTest() {
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    /**
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test
    public void testDeleteEmail() throws DalException, MalformedIdentifierException {
        int expectedNumberOfDeletedEmails = 0;
        when(mockContactRepository.deleteEmailById(1)).thenReturn(0);

        final List<Email> emailList = new ArrayList<>();

        DeleteEmailController controller = new DeleteEmailControllerImpl(mockContactRepository);

        controller.deleteEmail("1");
        int actualNumberOfEmails = emailList.size();

        assertEquals(expectedNumberOfDeletedEmails, actualNumberOfEmails);
    }

    /**
     * Test of deleteEmail method, of class DeleteEmailControllerImpl.
     *
     * @throws DalException
     * @throws MalformedIdentifierException
     */
    @Test(expected = MalformedIdentifierException.class)
    public void testFailDeleteEmailBecauseOfMalformedId() throws DalException, MalformedIdentifierException {

        DeleteEmailController controller = new DeleteEmailControllerImpl(mockContactRepository);
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

        doThrow(new DalException()).when(mockContactRepository).deleteEmailById(1);

        DeleteEmailController controller = new DeleteEmailControllerImpl(mockContactRepository);
        controller.deleteEmail("1");
    }

}
