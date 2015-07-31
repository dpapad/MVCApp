package org.mypackage.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mypackage.application.ApplicationDependencies;
import org.mypackage.application.errors.MalformedIdentifierException;
import org.mypackage.controller.DeleteContactController;
import org.mypackage.dal.DalException;

/**
 *
 * @author dpa
 */
@WebServlet(name = "DeleteContactServlet", urlPatterns = {"/deleteContact"})
public class DeleteContactServlet extends HttpServlet {

    private DeleteContactController deleteContactController;
    
    public DeleteContactServlet(){
        this(ApplicationDependencies.CONTROLLER_FACTORY.createDeleteContactController());
    }
    public DeleteContactServlet(DeleteContactController deleteController){
        this.deleteContactController = deleteController;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        try {
            deleteContactController.deleteContact(request.getParameter("contactId"));

            String redirectUrl = this.getServletContext().getContextPath() + "/contacts";
            response.sendRedirect(redirectUrl);
        } catch (DalException ex) {
            throw new ServletException("A database error occured", ex);
//            request.setAttribute("exception", ERROR_EXCEPTION);
//            request.setAttribute("servlet", ERROR_SERVLET_NAME);
//            request.setAttribute("requestUri", ERROR_REQUEST_URI);
//            request.setAttribute("errorMessage", ERROR_MESSAGE);
//
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (MalformedIdentifierException ex) {
            throw new ServletException("A database error occured", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
