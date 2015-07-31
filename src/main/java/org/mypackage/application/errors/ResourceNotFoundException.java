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
public class ResourceNotFoundException extends Exception {

    private final Object resourceId;

    public ResourceNotFoundException(Object resourceId) {
        this.resourceId = resourceId;
    }

    public Object getResourceId() {
        return resourceId;
    }

}
