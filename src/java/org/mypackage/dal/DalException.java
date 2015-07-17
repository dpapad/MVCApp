/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.dal;

/**
 *
 * @author dpa
 */
public class DalException extends Exception {
    
    private String message;
    private Throwable cause;

    public DalException() {
    }

    public DalException(String message) {
        super(message);
        this.message = message;
    }

    public DalException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public DalException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }
    
}
