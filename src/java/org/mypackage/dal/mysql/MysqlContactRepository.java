package org.mypackage.dal.mysql;

import org.mypackage.dal.sql.SqlConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dpa
 */
public class MysqlContactRepository implements ContactRepository {

    private final SqlConnectionProvider connectionProvider;
    private String excMessage;
    
    
    public MysqlContactRepository(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
    
    @Override
    public void addContact(Contact c) throws DalException {
        Connection con = null;
        PreparedStatement contactPstmt = null;
        PreparedStatement emailPstmt = null;
        
        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    contactPstmt = con.prepareStatement("INSERT INTO contacts(contid, fullname, nickname, notes) VALUES(?,?,?,?)");
                    contactPstmt.setInt(1, (NumberOfContacts()+1));
                    contactPstmt.setString(2, c.getFullname());
                    contactPstmt.setString(3, c.getNickname());
                    contactPstmt.setString(4, c.getNotes());
                    
                    contactPstmt.execute();                   
                }
                finally {
                    if (contactPstmt != null) {
                        contactPstmt.close();
                    }
                }
            } 
            finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Error inserting a new contact into the database.";
            DalException addContactException = new DalException(excMessage, ex);
            Logger.getLogger("Error in method addContact() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, addContactException);
            throw addContactException;
        }
    }

    @Override
    public void deleteContactById(int id) throws DalException{
        Connection con = null;
        Statement stmt = null;
        try{
            try {
                con = this.connectionProvider.createConnection();
                try{
                    stmt = con.createStatement();
                    stmt.execute("DELETE FROM contacts WHERE contid=" + String.valueOf(id));
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if(con != null){
                    con.close();
                }
            }
            
        }
        catch (SQLException | ClassNotFoundException ex){
            
            excMessage = "Error deleting contact by ID.";
            DalException deleteContactByIdException = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in method deleteContactById() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, deleteContactByIdException);
            throw deleteContactByIdException;
        }
    }

    @Override
    public void updateContact(Contact c) throws DalException{
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            try{
                con = this.connectionProvider.createConnection();
                try {
                    pstmt = con.prepareStatement("UPDATE contacts SET fullname=?, nickname=?, notes=? WHERE contid=?");
                    pstmt.setString(1, c.getFullname());
                    pstmt.setString(2, c.getNickname());
                    pstmt.setString(3, c.getNotes());
                    pstmt.setInt(4, c.getContid());
                    pstmt.executeUpdate();
                } finally {
                    if(pstmt!=null){
                        pstmt.close();
                    }
                }
            } finally {
                    if(con != null){
                con.close();
                    }
            }
        } catch( Exception ex) {
            excMessage = "Error updating the contact.";
            
            DalException updateContactException = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in method updateContact() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, updateContactException);
            
            throw updateContactException;
        }
    }

    @Override
    public Contact getContactById(int id) throws DalException{
        Contact contact = null;
        Connection con = null;
        Statement stmt = null;
        try {           
            try{
                con = this.connectionProvider.createConnection();
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM contacts WHERE contid=" + id);
                    if (rs.next()) {
                        contact = new Contact();
                        contact.setContid(rs.getInt(1));
                        contact.setFullname(rs.getString(2));
                        contact.setNickname(rs.getString(3));
                        contact.setNotes(rs.getString(4));
                    }
                    
                } finally {
                    if(stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Error retrieving contact by ID.";
            DalException getContactByIdException = new DalException(excMessage, ex);
            
            Logger.getLogger("Error in method getContactById() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, getContactByIdException);
            throw getContactByIdException;
        }
        
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() throws DalException{
        List<Contact> list = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        
        try {
            try{
                con = this.connectionProvider.createConnection();
                try{
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM contacts ORDER BY contid");
                    while(rs.next()) {
                        Contact contact = new Contact();
                        contact.setContid(rs.getInt(1));
                        contact.setFullname(rs.getString(2));
                        contact.setNickname(rs.getString(3));
                        contact.setNotes(rs.getString(4));
                        list.add(contact);
                    }
                } finally {
                    if(stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if(con != null) {
                    con.close();
                }
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Error retrieving all contacts from the database.";
            DalException getAllContactsException = new DalException(excMessage, ex);
            Logger.getLogger("Error in method getAllContacts() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, getAllContactsException);
            throw getAllContactsException;
        }
        
        return list;
    }
    
    @Override
    public void addEmail (Email e)  throws DalException{
        Connection con = null;
        PreparedStatement emailPstmt = null;
        
        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    
                    emailPstmt = con.prepareStatement("INSERT INTO emails(emailid, address, email_type, contid) VALUES(?,?,?,?)");
                    emailPstmt.setInt(1, (NumberOfEmails()+1));
                    emailPstmt.setString(2, e.getAddress());
                    emailPstmt.setString(3, e.getType());
                    //Contact id
                    emailPstmt.setInt(4, e.getContid());
                    emailPstmt.execute();
                                        
                }
                finally {
                                 
                    if (emailPstmt != null) {
                        emailPstmt.close();
                    }
                }
            } 
            finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Error inserting a new email into the database.";
            DalException addEmailException = new DalException(excMessage, ex);
            Logger.getLogger("Error in method addEmail() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, addEmailException);
            throw addEmailException;
        }
    }
    

     @Override
    public List<Email> getAllEmailsByContactId(int id) throws DalException{
        List<Email> list = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        
        try {
            try{
                con = this.connectionProvider.createConnection();
                try{
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM emails WHERE contid=" + id);
                    while(rs.next()) {
                        Email email = new Email();
                        email.setEmailid(rs.getInt(1));
                        email.setAddress(rs.getString(2));
                        email.setType(rs.getString(3));
                        email.setContid(rs.getInt(4));
                        list.add(email);
                    }
                } finally {
                    if(stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if(con != null) {
                    con.close();
                }
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Error retrieving all emails for this contact from the database.";
            DalException getAllEmailsByContactError = new DalException(excMessage, ex);
            Logger.getLogger("Error in method getAllEmailsByContactId() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, getAllEmailsByContactError);
            throw getAllEmailsByContactError;
        }
        
        return list;
    }
    
    public int NumberOfContacts() throws DalException {
        Connection con = null;
        Statement stmt = null;
        int contactEntries = 0;
        try {
            try {
                con = this.connectionProvider.createConnection();
                
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM contacts");
                    while(rs.next()) {
                        
                        contactEntries = rs.getInt(1);
                    }
                } finally {
                    if(stmt!=null) {
                        stmt.close();
                    }
                }
            } finally {
                if(con != null) {
                    con.close();
                }
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Couldn't execute \"SELECT * FROM contacts\" query.";
            DalException numberOfContactsException = new DalException(ex);
            Logger.getLogger("Error in method NumberOfContacts() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, numberOfContactsException);
            throw numberOfContactsException;
        }
        
        return contactEntries;
    }
    
    public int NumberOfEmails() throws DalException {
        Connection con = null;
        Statement stmt = null;
        int emailEntries = 0;
        try {
            try {
                con = this.connectionProvider.createConnection();
                
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM emails");
                    while(rs.next()) {
                        emailEntries = rs.getInt(1);
                    }
                } finally {
                    if(stmt!=null) {
                        stmt.close();
                    }
                }
            } finally {
                if(con != null) {
                    con.close();
                }
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            excMessage = "Couldn't execute \"SELECT * FROM emails\" query.";
            DalException numberOfEmailsException = new DalException(excMessage, ex);
            Logger.getLogger("Error in method NumberOfEmails() at " + MysqlContactRepository.class.getName()).log(Level.SEVERE,"Message returned: " + excMessage, numberOfEmailsException);

            throw numberOfEmailsException;
        }
        
        return emailEntries;
    }
    
}
