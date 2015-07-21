package org.mypackage.model;

/**
 *
 * @author dpa
 */
public class Email {
    private int id;
    
    private String address;
    
    private String category;
    
    private int fContactId;
    
    public Email() {
    }
    
    public Email(int emailId, String address, String type, int contactId) {
        this.id = emailId;
        this.address = address;
        this.category = type;
        this.fContactId = contactId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param Id the id to set
     */
    public void setId(int Id) {
        this.id = Id;
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
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the fContactId
     */
    public int getfContactId() {
        return fContactId;
    }

    /**
     * @param fContactId the fContactId to set
     */
    public void setfContactId(int fContactId) {
        this.fContactId = fContactId;
    }
}
