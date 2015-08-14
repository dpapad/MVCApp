package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
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
    private final static Logger logger = Logger.getLogger(NewEmailServlet.class);
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
 
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String address = request.getParameter("address");
        String categoryValue = request.getParameter("category");
        String contactId = request.getParameter("contactId");

        try {
            int contId = newEmailController.addNewEmail(address, categoryValue, contactId);
            request.getSession().removeAttribute("errorMessage");
            String redirectUrl = this.getServletContext().getContextPath() + "/contacts/" + contId;
            response.sendRedirect(redirectUrl);

        } catch (DalException ex) {
            logger.error("An error occured while trying to add a new email for contact with ID = " + contactId
                    + "Email object parameters: " 
                    + "/nAddress: " + address
                    + "/nCategory value (enum): " + categoryValue
                    + "/nfContactId: " + contactId, ex);
            request.setAttribute("errorCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("errorMessage", "An internal database error occured. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMessage", "An error occured because of a malformed id. Please use only numeric values.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (MalformedCategoryException ex) {
            request.setAttribute("errorCode", HttpServletResponse.SC_CONFLICT);
            request.setAttribute("errorMessage", "An internal conflict concerning the category of the email occured. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (DuplicateEmailException ex) {
            request.getSession().setAttribute("errorMessage", "This address already exists. Please enter a new one.");
            response.sendRedirect(request.getHeader("Referer"));
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
