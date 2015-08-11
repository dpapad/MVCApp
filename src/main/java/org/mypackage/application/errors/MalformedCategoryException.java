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
public class MalformedCategoryException extends Exception {

    /**
     * Creates a new instance of <code>MalformedCategoryException</code> without
     * detail message.
     */
    private final String identifier;
    public MalformedCategoryException(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Constructs an instance of <code>MalformedCategoryException</code> with
     * the specified detail message.
     *
     * @param identifier
     * @param message the detail message.
     */
    public MalformedCategoryException(String identifier, String message) {
        super(message);
        this.identifier = identifier;
    }
    
    public MalformedCategoryException(String identifier, String message, Throwable cause) {
        super(message, cause);
        this.identifier = identifier;
    }
    
    public MalformedCategoryException(String identifier, Throwable cause) {
        super(cause);
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return identifier;
    }
}
