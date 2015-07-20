package org.mypackage.model;

/**
 *
 * @author dpa
 */
public class Contact {

    private int contid;
    private String fullname;
    private String nickname;
    private String notes;

    public Contact() {              
    }
    
    public Contact(int contid, String fullname, String nickname, String notes) {
        this.contid = contid;
        this.fullname = fullname;
        this.nickname = nickname;
        this.notes = notes;
    }

    /**
     * @return the contid
     */
    public int getContid() {
        return contid;
    }
    
    /**
     * @param contid the contid to set
     */
    public void setContid(int contid) {
        this.contid = contid;
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
