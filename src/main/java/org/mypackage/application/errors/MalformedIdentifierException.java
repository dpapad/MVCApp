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
public class MalformedIdentifierException extends Exception {
    
    private final String identifier;

    public MalformedIdentifierException(String identifier) {
        this.identifier = identifier;
    }

    public MalformedIdentifierException(String identifier, String message) {
        super(message);
        this.identifier = identifier;
    }

    public MalformedIdentifierException(String identifier, String message, Throwable cause) {
        super(message, cause);
        this.identifier = identifier;
    }

    public MalformedIdentifierException(String identifier, Throwable cause) {
        super(cause);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

}
