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
import org.mypackage.controller.NewContactController;
import org.mypackage.dal.DalException;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewContactServlet", urlPatterns = {"/newContact"})
public class NewContactServlet extends HttpServlet {

    private NewContactController newContactController;

    public NewContactServlet() {
        this(ApplicationDependencies.CONTROLLER_FACTORY.createNewContactController());
    }

    public NewContactServlet(NewContactController newContactController) {
        this.newContactController = newContactController;
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newContact.jsp").forward(request, response);
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String nickname = request.getParameter("nickname");
        String notes = request.getParameter("notes");

        try {
            int c = newContactController.addNewContact(fullName, nickname, notes);

            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + c;
            response.sendRedirect(redirectUrl);
        } catch (DalException ex) {
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
    }// </editor-fold>

}
