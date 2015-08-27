/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.spring.controllers;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteEmailController;
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dev-dp
 */
@Controller
public class EmailsSpringController {

    // log4j logger for error/exception logging
    private final static Logger logger = Logger.getLogger(EmailsSpringController.class);

    // (Dependency Injection) Autowired Service beans
    @Autowired
    ContactsSpringController contactsSpringController;
    @Autowired
    NewEmailController newEmailController;
    @Autowired
    DeleteEmailController deleteEmailController;

    // [handler] GET method for requests about creating a new mail 
    @RequestMapping(value = "/contacts/{id}/new_email", method = RequestMethod.GET)
    public ModelAndView getCreateNewEmail(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("contactId", id);
        modelAndView.addObject("mailCategories", Email.Category.values());
        modelAndView.setViewName("/newEmail.jsp");
        return modelAndView;
    }

    // [handler] POST method for requests about creating a new mail
    @RequestMapping(value = "/contacts/{id}/new_email_submitted", method = RequestMethod.POST)
    public ModelAndView postCreateNewEmail(@PathVariable String id, @RequestParam("address") String address, @RequestParam("category") String categoryValue, @RequestParam("contactId") String contactId) {
        ModelAndView modelAndView = new ModelAndView();
//        String state = "false";
        try {
            int newEmailId = newEmailController.addNewEmail(address, categoryValue, id);
//            state = "true";
            modelAndView.addObject("emailId", newEmailId);
//            modelAndView.addObject("state", state);
            modelAndView = contactsSpringController.getAContact(id);
            
        } catch (DalException ex) {
            logger.error("An error occured while trying to add a new email for contact with ID = " + id
                    + "Email object parameters: "
                    + "/nAddress: " + address
                    + "/nCategory value (enum): " + categoryValue
                    + "/nfContactId: " + contactId, ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "An internal database error occured. Please try again.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id." + id + " Please use only numeric values.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedCategoryException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_CONFLICT);
            modelAndView.addObject("errorMessage", "An internal conflict concerning the category of the email occured. Please try again.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (DuplicateEmailException ex) {
//            modelAndView.addObject("state", state);
            modelAndView = getCreateNewEmail(id);
            modelAndView.addObject("errorMessage", "This address already exists. Please enter a new one.");
        }
        return modelAndView;
    }

    // [handler] GET method for deleting emails with specific id (one at a time)
    @RequestMapping(value = "/contacts/{id}/delete_email/{emailId}", method = RequestMethod.GET)
    public ModelAndView getDeleteEmail(@PathVariable String id, @PathVariable String emailId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(id);
        modelAndView.setViewName("redirect:/contacts/" + id);
        try {
            this.deleteEmailController.deleteEmail(emailId);
            modelAndView.setViewName("/contacts/" + id);
        } catch (DalException ex) {
            logger.error("A database error occured while trying to delete contact with ID = " + id, ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }
}
