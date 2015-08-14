package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dpa
 */
@WebServlet(name = "ModifyContactServlet", urlPatterns = {"/modifyContact"})
public class ModifyContactServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(ModifyContactServlet.class);
    private ModifyContactController modifyContactController;

    public ModifyContactServlet() {
        this(ApplicationDependencies.CONTROLLER_FACTORY.createModifyContactController());
    }

    public ModifyContactServlet(ModifyContactController modifyContactController) {
        this.modifyContactController = modifyContactController;
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contactId = request.getParameter("contactId");
        String fullName = request.getParameter("fullname");
        String nickname = request.getParameter("nickname");
        String note = request.getParameter("notes");
        try {
            Contact contact = modifyContactController.modifyContact(contactId, fullName, nickname, note);

            request.setAttribute("contact", contact);

            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contact.getId();
            response.sendRedirect(redirectUrl);
        } catch (DalException ex) {
            logger.error("A database error occured while trying to update contact with ID = "+ contactId, ex);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values."
                    + "/nid that caused the error: " + request.getParameter("contactId"));
        }

    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("contactId");
        try {
            Contact contact = modifyContactController.retrieveContact(id);

            request.setAttribute("contact", contact);

            request.getRequestDispatcher("/modifyContact.jsp").forward(request, response);

        } catch (DalException ex) {
            logger.error("A database error occured while trying to retrieve contact with ID = " + id, ex);
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "There was a an internal database error.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
