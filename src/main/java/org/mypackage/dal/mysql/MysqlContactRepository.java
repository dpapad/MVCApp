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
    public int addContact(Contact c) throws DalException {
        Connection con = null;
        PreparedStatement addContactStmt = null;
        int contactId;

        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    addContactStmt = con.prepareStatement("INSERT INTO Contact (FullName, Nickname, Notes) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    addContactStmt.setString(1, c.getFullName());
                    addContactStmt.setString(2, c.getNickname());
                    addContactStmt.setString(3, c.getNotes());

                    addContactStmt.execute();

                    ResultSet rs = addContactStmt.getGeneratedKeys();
                    rs.next();
                    contactId = rs.getInt(1);

                } finally {
                    if (addContactStmt != null) {
                        addContactStmt.close();
                    }
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DalException addContactException = new DalException(ex);
            throw addContactException;
        }

        return contactId;
    }

    @Override
    public void deleteContactById(int id) throws DalException {
        Connection con = null;
        PreparedStatement deleteContactStmt = null, deleteMailsStmt = null;
        try {
            try {
                con = this.connectionProvider.createConnection();
                con.setAutoCommit(false);

                try {
                    try {
                        deleteMailsStmt = con.prepareStatement("DELETE FROM Emails WHERE fContactId = ?");
                        deleteMailsStmt.setInt(1, id);
                        deleteMailsStmt.executeUpdate();
                    } finally {
                        if (deleteMailsStmt != null) {
                            deleteMailsStmt.close();
                        }
                    }

                    try {
                        deleteContactStmt = con.prepareStatement("DELETE FROM Contact WHERE Id = ?");
                        deleteContactStmt.setInt(1, id);
                        deleteContactStmt.executeUpdate();
                    } finally {
                        if (deleteContactStmt != null) {
                            deleteContactStmt.close();
                        }
                    }

                    con.commit();
                } catch (SQLException ex) {
                    con.rollback();
                    throw ex;
                } finally {
                    con.setAutoCommit(true);
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            DalException deleteContactByIdException = new DalException(ex);
            throw deleteContactByIdException;
        }
    }

    @Override
    public void updateContact(Contact c) throws DalException {
        Connection con = null;
        PreparedStatement udpateContactStmt = null;
        try {
            try {
                con = this.connectionProvider.createConnection();
                try {
                    udpateContactStmt = con.prepareStatement("UPDATE Contact SET FullName=?, Nickname=?, Notes=? WHERE Id=?");
                    udpateContactStmt.setString(1, c.getFullName());
                    udpateContactStmt.setString(2, c.getNickname());
                    udpateContactStmt.setString(3, c.getNotes());
                    udpateContactStmt.setInt(4, c.getId());
                    udpateContactStmt.executeUpdate();
                } finally {
                    if (udpateContactStmt != null) {
                        udpateContactStmt.close();
                    }
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DalException updateContactException = new DalException(ex);
            throw updateContactException;
        }
    }

    @Override
    public Contact getContactById(int id) throws DalException {
        Contact contact = null;
        Connection con = null;
        PreparedStatement getContactStmt = null;
        try {
            try {
                con = this.connectionProvider.createConnection();
                try {
                    getContactStmt = con.prepareStatement("SELECT * FROM Contact WHERE Id= ?");
                    getContactStmt.setInt(1, id);
                    ResultSet rs = getContactStmt.executeQuery();
                    if (rs.next()) {
                        contact = new Contact();
                        contact.setId(rs.getInt(1));
                        contact.setFullName(rs.getString(2));
                        contact.setNickname(rs.getString(3));
                        contact.setNotes(rs.getString(4));
                    }

                } finally {
                    if (getContactStmt != null) {
                        getContactStmt.close();
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
    public List<Contact> getAllContacts() throws DalException {
        List<Contact> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement getAllContactsStmt = null;

        try {
            try {
                con = this.connectionProvider.createConnection();
                try {
                    getAllContactsStmt = con.prepareStatement("SELECT * FROM Contact ORDER BY Id");
                    ResultSet rs = getAllContactsStmt.executeQuery();
                    while (rs.next()) {
                        Contact contact = new Contact();
                        contact.setId(rs.getInt(1));
                        contact.setFullName(rs.getString(2));
                        contact.setNickname(rs.getString(3));
                        contact.setNotes(rs.getString(4));
                        list.add(contact);
                    }
                } finally {
                    if (getAllContactsStmt != null) {
                        getAllContactsStmt.close();
                    }
                }
            } finally {
                if (con != null) {
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
    public int addEmail(Email e) throws DalException {
        int addedEmailId;
        Connection con = null;
        PreparedStatement emailPstmt = null;

        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    emailPstmt = con.prepareStatement("INSERT INTO Emails(Address, Category, fContactId) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    emailPstmt.setString(1, e.getAddress());

                    emailPstmt.setInt(2, e.getCategory().getByteValue());

                    emailPstmt.setInt(3, e.getfContactId());
                    emailPstmt.execute();

                    ResultSet rs = emailPstmt.getGeneratedKeys();
                    rs.next();
                    addedEmailId = rs.getInt(1);
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
        } catch (SQLException | ClassNotFoundException ex) {
            DalException addEmailException = new DalException(ex);
            throw addEmailException;
        }

        return addedEmailId;
    }

    @Override
    public List<Email> getAllEmailsByContactId(int id) throws DalException {
        List<Email> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement getAllEmailsByContactIdStmt = null;

        try {
            try {
                con = this.connectionProvider.createConnection();
                try {
                    getAllEmailsByContactIdStmt = con.prepareStatement("SELECT * FROM Emails WHERE fContactId= ?");
                    getAllEmailsByContactIdStmt.setInt(1, id);
                    ResultSet rs = getAllEmailsByContactIdStmt.executeQuery();
                    while (rs.next()) {
                        Email email = new Email();
                        email.setId(rs.getInt(1));
                        email.setAddress(rs.getString(2));
                        byte categoryValue = rs.getByte(3);
                        email.setCategory(Email.Category.forValue(categoryValue));
                        email.setfContactId(rs.getInt(4));
                        list.add(email);
                    }
                } finally {
                    if (getAllEmailsByContactIdStmt != null) {
                        getAllEmailsByContactIdStmt.close();
                    }
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            DalException getAllEmailsByContactError = new DalException(ex);
            throw getAllEmailsByContactError;
        }

        return list;
    }

    @Override
    public void deleteEmailById(int id) {
        Connection con = null;
        PreparedStatement deleteEmailStmt = null;
        try {
            try {
                con = this.connectionProvider.createConnection();

                try {
                    deleteEmailStmt = con.prepareStatement("DELETE FROM Emails WHERE Id = ?");
                    deleteEmailStmt.setInt(1, id);
                    deleteEmailStmt.execute();
                } finally {
                    if (deleteEmailStmt != null) {
                        deleteEmailStmt.close();
                    }
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DalException deleteEmailException = new DalException(ex);
            //throw deleteEmailException;
        }
    }

    //Fixed
    @Override
    public boolean checkIfEmailExists(Email email) {
        Connection connection = null;
        PreparedStatement checkIfEmailExistsStmt = null;
        boolean result = false;
        
        try {
            try {
                connection = this.connectionProvider.createConnection();

                try {
                    checkIfEmailExistsStmt = connection.prepareStatement("SELECT EXISTS(SELECT * FROM Emails WHERE Address=?);");
                    checkIfEmailExistsStmt.setString(1, email.getAddress());

                    ResultSet rs = checkIfEmailExistsStmt.executeQuery();
                    rs.next();
                        
                    result = (rs.getInt(1) == 1);
                    return result;
                } finally {
                    if (checkIfEmailExistsStmt != null) {
                        checkIfEmailExistsStmt.close();
                    }
                }
            } 
            finally {
                if (connection != null) {
                    connection.close();
                }
            }

        } 
        catch (Exception ex) {
        }
        return result;
    }

}
