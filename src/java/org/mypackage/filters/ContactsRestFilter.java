package org.mypackage.filters;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Nikolaos Nakas <nn@eworx.gr>
 */
public class ContactsRestFilter implements Filter {

    private static final Pattern REST_PATTERN;
    
    static {
        REST_PATTERN = Pattern.compile("/contacts(?:/([^/]+))?/?$");
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            return;
        }
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Matcher m = REST_PATTERN.matcher(httpRequest.getRequestURI());
        
        if (m.find()) {
            String contactId = m.group(1);
            request.setAttribute("contactId", contactId);
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
    
}
