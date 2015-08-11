/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.application.errors;

/**
 *
 * @author dev-dp
 */
public class DuplicateEmailException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateEmailException</code> without
     * detail message.
     */
    private final Object resourceId;
    
    public DuplicateEmailException(Object resourceId) {
        this.resourceId = resourceId;
    }
    
    public Object getResourceId() {
        return resourceId;
    }
}
