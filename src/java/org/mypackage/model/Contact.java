package org.mypackage.model;

/**
 *
 * @author dpa
 */
public class Contact {

    private int contactId;
    private String fullname;
    private String nickname;
    private String notes;

    public Contact() {              
    }
    
    public Contact(int contactId, String fullname, String nickname, String notes) {
        this.contactId = contactId;
        this.fullname = fullname;
        this.nickname = nickname;
        this.notes = notes;
    }

    /**
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }
    
    /**
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
