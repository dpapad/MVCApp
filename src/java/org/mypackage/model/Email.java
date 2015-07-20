package org.mypackage.model;

/**
 *
 * @author dpa
 */
public class Email {
    private int emailId;
    
    private String address;
    
    private String type;
    
    private int contactId;
    
    public Email() {
    }
    
    public Email(int emailId, String address, String type, int contactId) {
        this.emailId = emailId;
        this.address = address;
        this.type = type;
        this.contactId = contactId;
    }

    /**
     * @return the emailId
     */
    public int getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(int emailId) {
        this.emailId = emailId;
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
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the cont_id to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
