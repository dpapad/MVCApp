/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.model;

/**
 *
 * @author dpa
 */




public class Email {
    private int emailid;
    
    private String address;
    
    private String type;
    
    private int contid;
    
    
    public Email() {
        this.emailid = emailid;
        this.address = address;
        this.type = type;
        this.contid = contid;
    }

    /**
     * @return the emailid
     */
    public int getEmailid() {
        return emailid;
    }

    /**
     * @param emailid the emailid to set
     */
    public void setEmailid(int emailid) {
        this.emailid = emailid;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the contid
     */
    public int getContid() {
        return contid;
    }

    /**
     * @param contid the cont_id to set
     */
    public void setContid(int contid) {
        this.contid = contid;
    }
}
