package org.mypackage.view;

import java.io.IOException;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static javax.servlet.RequestDispatcher.ERROR_SERVLET_NAME;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            throw new ServletException("A database error occured.", ex);
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
            throw new ServletException("A database error occured", ex);
        } catch (MalformedIdentifierException ex) {
            throw new ServletException("A database error occured", ex);
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
