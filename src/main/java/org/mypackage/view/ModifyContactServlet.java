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
import org.mypackage.controller.ModifyContactController;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;

/**
 *
 * @author dpa
 */
@WebServlet(name = "ModifyContactServlet", urlPatterns = {"/modifyContact"})
public class ModifyContactServlet extends HttpServlet {

    public ModifyContactController modifyContactController = new ModifyContactController();

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
            request.setAttribute("exception", ERROR_EXCEPTION);
            request.setAttribute("servlet", ERROR_SERVLET_NAME);
            request.setAttribute("requestUri", ERROR_REQUEST_URI);
            request.setAttribute("errorMessage", ERROR_MESSAGE);

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("contactId");
        try {
            Contact contact = modifyContactController.retrieveContact(id);

            request.setAttribute("contact", contact);

            request.getRequestDispatcher("/modifyContact.jsp").forward(request, response);

        } catch (DalException | NumberFormatException ex) {
            request.setAttribute("exception", ERROR_EXCEPTION);
            request.setAttribute("servlet", ERROR_SERVLET_NAME);
            request.setAttribute("requestUri", ERROR_REQUEST_URI);
            request.setAttribute("errorMessage", ERROR_MESSAGE);

            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
