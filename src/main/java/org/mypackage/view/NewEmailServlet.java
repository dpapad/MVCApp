package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.application.errors.DuplicateEmailException;
import org.mypackage.application.errors.MalformedCategoryException;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.NewEmailController;
import org.mypackage.model.Email;
import org.mypackage.dal.DalException;

/**
 *
 * @author dpa
 */
@WebServlet(name = "NewEmailServlet", urlPatterns = {"/newEmail"})
public class NewEmailServlet extends HttpServlet {

    private NewEmailController newEmailController;

    public NewEmailServlet() {
        this(ApplicationDependencies.CONTROLLER_FACTORY.createNewEmailController());
    }

    public NewEmailServlet(NewEmailController newEmailController) {
        this.newEmailController = newEmailController;
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("mailCategories", Email.Category.values());
        request.getRequestDispatcher("/newEmail.jsp").forward(request, response);
    }

    //Fixed    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String address = request.getParameter("address");
        String categoryValue = request.getParameter("category");
        String contactId = request.getParameter("contactId");

        try {
            int contId = newEmailController.addNewEmail(address, categoryValue, contactId);

            if (contId > 0) {
                request.getSession().removeAttribute("errorMessage");
                String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contId;
                response.sendRedirect(redirectUrl);
            } else {
                request.getSession().setAttribute("errorMessage", "This address already exists. Please enter a new one.");
                response.sendRedirect(request.getHeader("Referer"));
            }

        } catch (DalException ex) {
            throw new ServletException("A database error occured", ex);
        } catch (MalformedIdentifierException ex) {
            throw new ServletException("A database error occured", ex);
        } catch (MalformedCategoryException ex) {
            throw new ServletException("Internal error", ex);
        } catch (DuplicateEmailException ex) {
            
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
