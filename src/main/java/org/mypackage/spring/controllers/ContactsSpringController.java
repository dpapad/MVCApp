/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.spring.controllers;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.application.errors.ResourceNotFoundException;
import org.mypackage.controller.ContactsController;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
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
// Multi-Action Controller responsible for handling requests about Contacts
@Controller
public class ContactsSpringController {

    // log4j logger for error/exception logging
    private final static Logger logger = Logger.getLogger(ContactsSpringController.class);

    // (Dependency Injection) Autowired Service objects
    @Autowired
    private ContactsController contactsController;
    @Autowired
    private DeleteContactController deleteContactController;
    @Autowired
    private NewContactController newContactController;
    @Autowired
    private ModifyContactController modifyContactController;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return getAllContacts();
        //return new ModelAndView("/contacts.jsp");
    }

    // Get all Contacts    
    // [handler] GET method that retrieves all contacts
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ModelAndView getAllContacts() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Contact> list = contactsController.retrieveAllContacts();
            modelAndView.addObject("contactsList", list);
            modelAndView.setViewName("/contacts.jsp");
        } catch (DalException ex) {
            logger.error("A database error occured.", ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

    // Get a contact
    // [handler] GET method that retrieves a specific contact, according to its ID
    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
    public ModelAndView getAContact(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Contact contact = contactsController.getContact(id);

            modelAndView.addObject("contact", contact);
            List<Email> emailsList = contactsController.retrieveAllEmails(id);
            modelAndView.addObject("emailList", emailsList);
            modelAndView.addObject("contactId", id);
            modelAndView.setViewName("/viewContact.jsp");

        } catch (DalException ex) {
            logger.error("A database error occured.", ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id (caused by id = " + id + "). Please use only numeric values.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (ResourceNotFoundException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject("errorMessage", "Requested contact (with id = " + id + ") does not exist.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

    // DELETE Contact handlers
    // [handler] GET method that deletes a contact, according to its ID
    @RequestMapping(value = "/contacts/{id}/delete", method = RequestMethod.GET)
    public ModelAndView getDeleteContact(@PathVariable String id) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            deleteContactController.deleteContact(id);
            modelAndView.setViewName("redirect:/contacts");
        } catch (DalException ex) {
            logger.error("A database error occured.", ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "An internal database error occured. Please try again.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id (caused by id = " + id + "). Please use only numeric values.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

    // CREATE Contact handlers
    // [handler] GET method for creating a new contact
    @RequestMapping(value = "/contacts/new")
    public ModelAndView getCreateNewContact() {
        return new ModelAndView("/newContact.jsp");
    }

    // [handler] POST method for creating a new contact
    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public ModelAndView postCreateNewContact(@RequestParam("fullname") String fullName, @RequestParam("nickname") String nickname, @RequestParam("notes") String notes) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            int c = newContactController.addNewContact(fullName, nickname, notes);
            modelAndView.setViewName("redirect:/contacts/" + c);
        } catch (DalException ex) {
            logger.error("A database error occured while trying to create a contact with the following parameters:"
                    + "\nFull Name: " + fullName
                    + "\nNick name: " + nickname
                    + "\nNotes: " + notes, ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

    // UPDATE Contact Handlers
    // [handler] GET method used to retrieve a contact to be updated according to its ID
    @RequestMapping(value = "/contacts/{id}/modify", method = RequestMethod.GET)
    public ModelAndView getUpdateContact(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Contact contact = modifyContactController.retrieveContact(id);
            List emailsList = contactsController.retrieveAllEmails(id);
            modelAndView.addObject("contact", contact);
            modelAndView.addObject("emailsList", emailsList);
            modelAndView.addObject("mailCategories", Email.Category.values());
            modelAndView.setViewName("/modifyContact.jsp");
        } catch (DalException ex) {
            logger.error("A database error occured while trying to retrieve contact with ID = " + id, ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id (caused by id = " + id + "). Please use only numeric values.");
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

    // [handler] POST method used to update contact details/emails based on its ID
    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.POST)
    public ModelAndView postUpdateContact(@PathVariable String id, @RequestParam("contactId") String contactId, @RequestParam("fullname") String fullName, @RequestParam("nickname") String nickname, @RequestParam("notes") String note) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Contact contact = modifyContactController.modifyContact(contactId, fullName, nickname, note);
            modelAndView.addObject("contact", contact);
            modelAndView.setViewName("redirect:/contacts/" + contactId);
        } catch (DalException ex) {
            logger.error("A database error occured while trying to update contact with ID = " + contactId, ex);
            modelAndView.addObject("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelAndView.addObject("errorMessage", "There was a an internal database error.");
            modelAndView.setViewName("/errorPage.jsp");
        } catch (MalformedIdentifierException ex) {
            modelAndView.addObject("errorMessage", "An error occured because of a malformed id (caused by id = " + id + "). Please use only numeric values."
                    + "/nid that caused the error: " + contactId);
            modelAndView.setViewName("/errorPage.jsp");
        }
        return modelAndView;
    }

}
