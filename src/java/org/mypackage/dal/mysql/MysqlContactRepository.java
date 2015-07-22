package org.mypackage.dal.mysql;

import org.mypackage.dal.sql.SqlConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    
    
    public MysqlContactRepository(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
    
    @Override
    public void addContact(Contact c) throws DalException {
        Connection con = null;
        PreparedStatement contactPstmt = null;
        
        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    contactPstmt = con.prepareStatement("INSERT INTO Contact(Id, FullName, Nickname, Notes) VALUES(?,?,?,?)");
                    contactPstmt.setInt(1, (numberOfContacts()+1));
                    contactPstmt.setString(2, c.getFullName());
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
            DalException addContactException = new DalException(ex);
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
                    stmt.execute("DELETE FROM Contact WHERE Id=" + String.valueOf(id));
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
            DalException deleteContactByIdException = new DalException(ex);
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
                    pstmt = con.prepareStatement("UPDATE Contact SET FullName=?, Nickname=?, Notes=? WHERE Id=?");
                    pstmt.setString(1, c.getFullName());
                    pstmt.setString(2, c.getNickname());
                    pstmt.setString(3, c.getNotes());
                    pstmt.setInt(4, c.getId());
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
        } catch(SQLException | ClassNotFoundException ex) {
            DalException updateContactException = new DalException(ex);
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
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Contact WHERE Id=" + id);
                    if (rs.next()) {
                        contact = new Contact();
                        contact.setId(rs.getInt(1));
                        contact.setFullName(rs.getString(2));
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
            DalException getContactByIdException = new DalException(ex);
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
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Contact ORDER BY Id");
                    while(rs.next()) {
                        Contact contact = new Contact();
                        contact.setId(rs.getInt(1));
                        contact.setFullName(rs.getString(2));
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
            DalException getAllContactsException = new DalException(ex);
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
                    
                    emailPstmt = con.prepareStatement("INSERT INTO Emails(Id, Address, Category, fContactId) VALUES(?,?,?,?)");
                    emailPstmt.setInt(1, (numberOfEmails()+1));
                    emailPstmt.setString(2, e.getAddress());
                    
                    emailPstmt.setInt(3, e.getCategory().ordinal());
                    //Contact id
                    emailPstmt.setInt(4, e.getfContactId());
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
            DalException addEmailException = new DalException(ex);
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
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Emails WHERE fContactId=" + id);
                    while(rs.next()) {
                        Email email = new Email();
                        email.setId(rs.getInt(1));
                        email.setAddress(rs.getString(2));
                        email.setCategory(rs.getInt(3));
                        email.setfContactId(rs.getInt(4));
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
            DalException getAllEmailsByContactError = new DalException(ex);
            throw getAllEmailsByContactError;
        }
        
        return list;
    }
    
    public int numberOfContacts() throws DalException {
        Connection con = null;
        Statement stmt = null;
        int contactEntries = 0;
        try {
            try {
                con = this.connectionProvider.createConnection();
                
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Contact");
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
            DalException numberOfContactsException = new DalException(ex);
            throw numberOfContactsException;
        }
        
        return contactEntries;
    }
    
    public int numberOfEmails() throws DalException {
        Connection con = null;
        Statement stmt = null;
        int emailEntries = 0;
        try {
            try {
                con = this.connectionProvider.createConnection();
                
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Emails");
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
            DalException numberOfEmailsException = new DalException(ex);
            throw numberOfEmailsException;
        }
        
        return emailEntries;
    }
    
}
