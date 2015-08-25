package org.mypackage.dal.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author dpa
 */
/**
 * implements ContactRepository
 */
@Component
public class MysqlContactRepository implements ContactRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addContact(final Contact contact) throws DalException {
        int contactId;
        // Key to hold id value
        KeyHolder idKey = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement addContactStmt = connection.prepareStatement("INSERT INTO Contact (FullName, Nickname, Notes) VALUES (?,?,?)");
                    addContactStmt.setString(1, contact.getFullName());
                    addContactStmt.setString(2, contact.getNickname());
                    addContactStmt.setString(3, contact.getNotes());
                    return addContactStmt;
                }
            }, idKey);

            contactId = idKey.getKey().intValue();

        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return contactId;
    }

    @Override
    @Transactional
    public int deleteContactById(final int id) throws DalException {

        int totalAffectedRows;
        int contactRowsDeleted;
        int emailRowsDeleted;
        try {
            // Delete the emails first
            emailRowsDeleted = jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement deleteAllEmailsByContactIdPreparedStatement = connection.prepareStatement("DELETE FROM Emails WHERE fContactId = ?");
                    deleteAllEmailsByContactIdPreparedStatement.setInt(1, id);
                    return deleteAllEmailsByContactIdPreparedStatement;
                }
            });
            // Then, delete the contact
            contactRowsDeleted = jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement deleteContactPreparedStatement = connection.prepareStatement("DELETE FROM Contact WHERE Id = ?");
                    deleteContactPreparedStatement.setInt(1, id);
                    return deleteContactPreparedStatement;
                }
            });
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        totalAffectedRows = emailRowsDeleted + contactRowsDeleted;
        
        return totalAffectedRows;
    }

    @Override
    public int updateContact(final Contact contact) throws DalException {
        int updatedContactRows;
        try {
            updatedContactRows = jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement updateContactStatement = connection.prepareStatement("UPDATE Contact SET FullName=?, Nickname=?, Notes=?, WHERE Id=?");
                    updateContactStatement.setString(1, contact.getFullName());
                    updateContactStatement.setString(2, contact.getNickname());
                    updateContactStatement.setString(3, contact.getNotes());
                    updateContactStatement.setInt(4, contact.getId());

                    return updateContactStatement;
                }
            });
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return updatedContactRows;
    }

    @Override
    public Contact getContactById(final int id) throws DalException {
        Contact retrievedContact = null;
        try {
            retrievedContact = jdbcTemplate.queryForObject("SELECT * FROM Contact WHERE Id=?",
                    new Object[]{id},
                    new ContactMapper());
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return retrievedContact;
    }

    @Override
    public List<Contact> getAllContacts() throws DalException {
        List<Contact> listOfContacts = new ArrayList<>();
        try {
            listOfContacts = jdbcTemplate.query(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement getAllContactsPreparedStatement = connection.prepareStatement("SELECT * FROM Contact ORDER BY Id");
                    return getAllContactsPreparedStatement;
                }
            }, new ContactMapper());
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return listOfContacts;
    }

    @Override
    public int addEmail(final Email email) throws DalException {
        int addedEmailId;
        KeyHolder idKey = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement addEmailPreparedStatement = connection.prepareStatement("INSERT INTO Emails (Address, Category, fContactId) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    addEmailPreparedStatement.setString(1, email.getAddress());
                    addEmailPreparedStatement.setByte(2, email.getCategory().getByteValue());
                    addEmailPreparedStatement.setInt(3, email.getfContactId());

                    return addEmailPreparedStatement;
                }
            }, idKey);

            addedEmailId = idKey.getKey().intValue();

        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return addedEmailId;
    }

    @Override
    public List<Email> getAllEmailsByContactId(final int id) throws DalException {
        List<Email> listOfEmails = new ArrayList<>();
        try {
            listOfEmails = jdbcTemplate.query(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement getAllEmailsByContactIdPreparedStatement = connection.prepareStatement("SELECT * FROM Emails WHERE fContactId=?");
                    getAllEmailsByContactIdPreparedStatement.setInt(1, id);
                    return getAllEmailsByContactIdPreparedStatement;
                }
            }, new EmailMapper());
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
            throw new DalException();
        }

        return listOfEmails;
    }

    @Override
    public int deleteEmailById(final int id) throws DalException {
        int deletedEmailRows;
        try {
            deletedEmailRows = jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement deleteEmailByIdPreparedStatement = connection.prepareStatement("DELETE FROM Emails WHERE Id=?");
                    deleteEmailByIdPreparedStatement.setInt(1, id);

                    return deleteEmailByIdPreparedStatement;
                }
            });
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return deletedEmailRows;
    }

    @Override
    public boolean checkIfEmailExists(Email email) throws DalException {
        boolean emailExists = false;

        try {
            int retrievedRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Emails WHERE Address=?", Integer.class, email.getAddress());
            emailExists = (retrievedRows == 1);
        } catch (DataAccessException ex) {
            throw new DalException(ex);
        }
        return emailExists;

    }

    private static final class ContactMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setFullName(rs.getString("FullName"));
            contact.setNickname(rs.getString("Nickname"));
            contact.setNotes(rs.getString("Notes"));

            return contact;
        }
    }

    private static final class EmailMapper implements RowMapper<Email> {

        @Override
        public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
            Email email = new Email();
            email.setId(rs.getInt(1));
            email.setAddress(rs.getString(2));
            byte categoryValue = rs.getByte(3);
            email.setCategory(Email.Category.forValue(categoryValue));
            email.setfContactId(rs.getInt(4));

            return email;
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
