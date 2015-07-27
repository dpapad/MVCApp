package org.mypackage.dal.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mypackage.dal.DalException;
import org.mypackage.dal.sql.SqlConnectionProvider;
import org.mypackage.model.Contact;

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
    public void setUp() throws SQLException, ClassNotFoundException  {
        Connection connection = null;

        PreparedStatement createContactTableStmt = null;

        try {
                connection = this.connectionProvider.createConnection();
                
                 try {
                        createContactTableStmt = connection.prepareStatement("CREATE TABLE Contact ("
                                + "Id INT primary key auto_increment, "
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

            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
    }

    @After
    public void tearDown() throws ClassNotFoundException, SQLException{
        Connection connection = null;

        PreparedStatement dropDatabaseStmt = null;

        try {

                connection = this.connectionProvider.createConnection();

                try {
                    dropDatabaseStmt = connection.prepareStatement("DROP TABLE Contact;");
                    dropDatabaseStmt.execute();
                } finally {
                    if (dropDatabaseStmt != null) {
                        dropDatabaseStmt.close();
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
        }
        finally {
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
        this.contactRepository.addContact(c);
        int contactId = this.getMaxContactId();
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
}
