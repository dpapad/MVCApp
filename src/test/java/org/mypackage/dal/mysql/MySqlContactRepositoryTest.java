package org.mypackage.dal.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.DalException;
import org.mypackage.model.Contact;
import org.mypackage.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author dev-dp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class MySqlContactRepositoryTest {

    @Autowired
    private JdbcTemplate testJdbcTemplate;
    @Autowired
    private ContactRepository testContactRepository;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        testJdbcTemplate.execute("CREATE TABLE Contact ("
                + "Id INT PRIMARY KEY AUTO_INCREMENT, "
                + "FullName VARCHAR(45), "
                + "Nickname VARCHAR(45), "
                + "Notes VARCHAR(45) "
                + ");");

        testJdbcTemplate.execute("CREATE TABLE Emails ("
                + "Id INT PRIMARY KEY AUTO_INCREMENT, "
                + "Address VARCHAR(150) UNIQUE, "
                + "Category TINYINT, "
                + "fContactId INT, "
                + "FOREIGN KEY (fContactId) "
                + "REFERENCES Contact (Id) "
                + "ON UPDATE CASCADE); ");
    }

    @After
    public void tearDown() throws ClassNotFoundException, SQLException {
        testJdbcTemplate.execute("DROP TABLE Emails;");
        testJdbcTemplate.execute("DROP TABLE Contact;");
    }

    @Test
    public void testAddContact() throws Exception {

        Contact c = new Contact();
        c.setFullName("xxx");
        c.setNickname("xxx");
        c.setNotes("xxx");
        int contactId = this.testContactRepository.addContact(c);
        int maxContactId = this.getMaxContactId();

        assertEquals(contactId, maxContactId);

        Contact c2 = this.testContactRepository.getContactById(contactId);

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
        int expectedResult = this.testContactRepository.addContact(c);
        int actualResult = this.getContactCount();

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testAddEmail() throws DalException {

        //Create a contact
        Contact c = new Contact();
        c.setFullName("asdf");
        c.setNickname("asdf");
        c.setNotes("asdf");
        int id = this.testContactRepository.addContact(c);

        // Manually inreamented list of emails
        List<Email> myEmailList = new ArrayList<>();

        //Create a new email for the contact above
        Email emailDummy = new Email();
        emailDummy.setAddress("mail1@test.com");
        emailDummy.setCategory(Email.Category.WORK);
        emailDummy.setfContactId(id);

        myEmailList.add(emailDummy);
        this.testContactRepository.addEmail(emailDummy);

        // Create a second dummy email for testing
        Email emailDummy2 = new Email();
        emailDummy2.setAddress("mailasdf@test.com");
        emailDummy2.setCategory(Email.Category.WORK);
        emailDummy2.setfContactId(id);

        myEmailList.add(emailDummy2);
        int expectedResult = this.testContactRepository.addEmail(emailDummy2);

        int actualResult = this.getMaxEmailId();

        assertEquals(expectedResult, actualResult);

        List<Email> retrievedEmailList = this.testContactRepository.getAllEmailsByContactId(id);

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
        this.testContactRepository.addContact(c);

        Email e = new Email();
        e.setAddress("mail2@test.com");
        e.setCategory(Email.Category.PERSONAL);
        e.setfContactId(1);
        int expectedResult = this.testContactRepository.addEmail(e);
        int actualResult = this.getEmailCount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddDuplicateEmail() throws DalException {
        boolean exists;

        Contact c = new Contact();
        c.setFullName("asdf");
        c.setNickname("asdf");
        c.setNotes("asdf");
        this.testContactRepository.addContact(c);

        Email e = new Email();
        e.setAddress("mail@test.com");
        e.setCategory(Email.Category.PERSONAL);
        e.setfContactId(1);
        this.testContactRepository.addEmail(e);

        exists = this.testContactRepository.checkIfEmailExists(e);

        assertEquals(true, exists);

    }

    private int getMaxContactId() throws DataAccessException {

        Integer maxContactId;

        maxContactId = testJdbcTemplate.queryForObject("SELECT MAX(Id) FROM Contact;", Integer.class);

        return maxContactId.intValue();

    }

    private int getContactCount() throws DataAccessException {
        Integer contactCount;
        contactCount = testJdbcTemplate.queryForObject("SELECT COUNT(*) FROM Contact;", Integer.class);
        return contactCount.intValue();
    }

    private int getMaxEmailId() throws DataAccessException {

        Integer maxEmailId;
        maxEmailId = testJdbcTemplate.queryForObject("SELECT MAX(Id) FROM Emails;", Integer.class);
        return maxEmailId.intValue();
    }

    private int getEmailCount() throws DataAccessException {
        Integer emailCount;
        emailCount = testJdbcTemplate.queryForObject("SELECT COUNT(*) FROM Emails;", Integer.class);
        return emailCount.intValue();
    }

    private void sortEmailsById(List emailList) {
        Collections.sort(emailList, new EmailComparator());
    }

    class EmailComparator implements Comparator<Email> {

        @Override
        public int compare(Email e1, Email e2) {
            return e1.getId() - e2.getId();
        }
    }
}
