package org.mypackage.dal.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mypackage.dal.DalException;
import org.mypackage.dal.sql.SqlConnectionProvider;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;

/**
 *
 * @author dev-dp
 */
public class MySqlContactRepositoryTest {

    private SqlConnectionProvider connectionProvider;
    private MysqlContactRepository contactRepository;

    public MySqlContactRepositoryTest() {
        this.connectionProvider = new H2SqlConnectionProvider();
        this.contactRepository = new MysqlContactRepository(this.connectionProvider);
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        Connection connection = null;

        PreparedStatement createContactTableStmt = null;
        PreparedStatement createEmailsTableStmt = null;

        try {
            connection = this.connectionProvider.createConnection();

            try {
                createContactTableStmt = connection.prepareStatement("CREATE TABLE Contact ("
                        + "Id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "FullName VARCHAR(45), "
                        + "Nickname VARCHAR(45), "
                        + "Notes VARCHAR(45) "
                        + ");");
                createContactTableStmt.execute();
            } finally {
                if (createContactTableStmt != null) {
                    createContactTableStmt.close();
                }
            }

            try {
                createEmailsTableStmt = connection.prepareStatement("CREATE TABLE Emails ("
                        + "Id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "Address VARCHAR(150) UNIQUE, "
                        + "Category TINYINT, "
                        + "fContactId INT, "
                        + "FOREIGN KEY (fContactId) "
                        + "REFERENCES Contact (Id) "
                        + "ON UPDATE CASCADE); ");
                createEmailsTableStmt.execute();
            } finally {
                if (createEmailsTableStmt != null) {
                    createEmailsTableStmt.close();
                }
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @After
    public void tearDown() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        PreparedStatement dropEmailsTableStmt = null;
        PreparedStatement dropContactTableStmt = null;

        try {

            connection = this.connectionProvider.createConnection();

            try {
                dropEmailsTableStmt = connection.prepareStatement("DROP TABLE Emails;");
                dropEmailsTableStmt.execute();

            } finally {
                if (dropEmailsTableStmt != null) {
                    dropEmailsTableStmt.close();
                }
            }

            try {
                dropContactTableStmt = connection.prepareStatement("DROP TABLE Contact;");
                dropContactTableStmt.execute();
            } finally {
                if (dropContactTableStmt != null) {
                    dropContactTableStmt.close();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;

        try {
            connection = connectionProvider.createConnection();
            assertNotNull(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Test
    public void testAddContact() throws Exception {

        Contact c = new Contact();
        c.setFullName("xxx");
        c.setNickname("xxx");
        c.setNotes("xxx");
        int contactId = this.contactRepository.addContact(c);
        int maxContactId = this.getMaxContactId();

        assertEquals(contactId, maxContactId);

        Contact c2 = this.contactRepository.getContactById(contactId);

        assertEquals(c.getFullName(), c2.getFullName());
        assertEquals(c.getNickname(), c2.getNickname());
        assertEquals(c.getNotes(), c2.getNotes());

    }

    @Test
    public void testAddContact2() throws Exception {
        Contact c = new Contact();
        c.setFullName("yyy");
        c.setNickname("yyy");
        c.setNotes("yyy");
        this.contactRepository.addContact(c);
        int count = this.getContactCount();

        assertEquals(1, count);

    }

    @Test
    public void testAddEmail() throws Exception {

        //Create a contact
        Contact c = new Contact();
        c.setFullName("asdf");
        c.setNickname("asdf");
        c.setNotes("asdf");
        this.contactRepository.addContact(c);

        // Manually inreamented list of emails
        List<Email> myEmailList = new ArrayList<>();

        //Create a new email for the contact above
        Email emailDummy = new Email();
        emailDummy.setAddress("mail1@test.com");
        emailDummy.setCategory(Email.Category.WORK);
        emailDummy.setfContactId(1);

        myEmailList.add(emailDummy);
        this.contactRepository.addEmail(emailDummy);

        // Create a second dummy email for testing
        Email emailDummy2 = new Email();
        emailDummy2.setAddress("mailasdf@test.com");
        emailDummy2.setCategory(Email.Category.WORK);
        emailDummy2.setfContactId(1);

        myEmailList.add(emailDummy2);
        int emailId = this.contactRepository.addEmail(emailDummy2);

        int maxEmailId = this.getMaxEmailId();

        assertEquals(emailId, maxEmailId);

        List<Email> retrievedEmailList = this.contactRepository.getAllEmailsByContactId(1);

        assertEquals(myEmailList.size(), retrievedEmailList.size());

        // Sort email lists
        sortEmailsById(myEmailList);
        sortEmailsById(retrievedEmailList);

        assertEquals(myEmailList.size(), retrievedEmailList.size());

        Email emailForComparison1, emailForComparison2;
        int i = 1;

        while (i < myEmailList.size()) {
            emailForComparison1 = myEmailList.get(i);
            emailForComparison2 = retrievedEmailList.get(i);

            assertEquals(emailForComparison1.getAddress(), emailForComparison2.getAddress());
            assertEquals(emailForComparison1.getCategory(), emailForComparison2.getCategory());
            assertEquals(emailForComparison1.getfContactId(), emailForComparison2.getfContactId());
            i++;
        }

    }

    @Test
    public void testAddEmail2() throws DalException, SQLException, ClassNotFoundException {
        Contact c = new Contact();
        c.setFullName("asdf");
        c.setNickname("asdf");
        c.setNotes("asdf");
        this.contactRepository.addContact(c);

        Email e = new Email();
        e.setAddress("mail2@test.com");
        e.setCategory(Email.Category.PERSONAL);
        e.setfContactId(1);
        this.contactRepository.addEmail(e);
        int count = this.getEmailCount();

        assertEquals(1, count);
    }

    @Test
    public void testAddDuplicateEmail() throws DalException {
        boolean exists;

        Contact c = new Contact();
        c.setFullName("asdf");
        c.setNickname("asdf");
        c.setNotes("asdf");
        this.contactRepository.addContact(c);

        Email e = new Email();
        e.setAddress("mail@test.com");
        e.setCategory(Email.Category.PERSONAL);
        e.setfContactId(1);
        this.contactRepository.addEmail(e);

        exists = this.contactRepository.checkIfEmailExists(e);

        assertEquals(true, exists);

    }

    private int getMaxContactId() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement getMaxContactIdStmt = null;
        int maxContactId;

        try {
            connection = this.connectionProvider.createConnection();
            try {
                getMaxContactIdStmt = connection.prepareStatement("SELECT MAX(Id) FROM Contact");
                ResultSet rs = getMaxContactIdStmt.executeQuery();

                rs.next();

                maxContactId = rs.getInt(1);

            } finally {
                if (getMaxContactIdStmt != null) {
                    getMaxContactIdStmt.close();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return maxContactId;
    }

    private int getContactCount() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement getContactCountStmt = null;
        int contactCount;

        try {
            connection = this.connectionProvider.createConnection();
            try {
                getContactCountStmt = connection.prepareStatement("SELECT COUNT(*) FROM Contact");
                ResultSet rs = getContactCountStmt.executeQuery();

                rs.next();
                contactCount = rs.getInt(1);

            } finally {
                if (getContactCountStmt != null) {
                    getContactCountStmt.close();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return contactCount;
    }

    private int getMaxEmailId() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement getMaxEmailIdStmt = null;
        int maxEmailId;

        try {
            connection = this.connectionProvider.createConnection();
            try {
                getMaxEmailIdStmt = connection.prepareStatement("SELECT MAX(Id) FROM Emails;");
                ResultSet rs = getMaxEmailIdStmt.executeQuery();

                rs.next();
                maxEmailId = rs.getInt(1);
            } finally {
                if (getMaxEmailIdStmt != null) {
                    getMaxEmailIdStmt.close();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return maxEmailId;
    }

    private int getEmailCount() throws SQLException, ClassNotFoundException {
        int emailCount;
        Connection connection = null;
        PreparedStatement getEmailCountStmt = null;

        try {
            connection = this.connectionProvider.createConnection();

            try {
                getEmailCountStmt = connection.prepareStatement("SELECT COUNT(*) FROM Emails;");
                ResultSet rs = getEmailCountStmt.executeQuery();
                rs.next();
                emailCount = rs.getInt(1);
            } finally {
                if (getEmailCountStmt != null) {
                    getEmailCountStmt.close();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return emailCount;
    }

    private void sortEmailsById(List emailList) {
        Collections.sort(emailList, new EmailComparator());
        //Collections.sort(emailList);        
    }

    class EmailComparator implements Comparator<Email> {

        @Override
        public int compare(Email e1, Email e2) {
            return e1.getId() - e2.getId();
        }
    }

}
