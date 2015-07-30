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
import org.mypackage.model.Email;
import org.mypackage.controller.NewEmailController;
import org.mypackage.dal.DalException;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewEmailServlet", urlPatterns = {"/newEmail"})
public class NewEmailServlet extends HttpServlet {

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("mailCategories", Email.Category.values());
        request.getRequestDispatcher("/newEmail.jsp").forward(request, response);
    }

    //Fixed    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NewEmailController emailController = new NewEmailController();

        String address = request.getParameter("address");
        String categoryValue = request.getParameter("category");
        String contactId = request.getParameter("contactId");

        try {
            int contId = emailController.addNewEmail(address, categoryValue, contactId);

            if (contId > 0) {
                request.getSession().removeAttribute("errorMessage");
                String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contId;
                response.sendRedirect(redirectUrl);
            } else {
                request.getSession().setAttribute("errorMessage", "This address already exists. Please enter a new one.");
                response.sendRedirect(request.getHeader("Referer"));
            }

        } catch (NumberFormatException | DalException ex) {
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
