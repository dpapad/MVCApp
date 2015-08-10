package org.mypackage.dal.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.mypackage.dal.sql.SqlConnectionProvider;

/**
 *
 * @author dpa
 */
public class MySqlConnectionProvider implements SqlConnectionProvider {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mvcproject";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "adminadmin";

    @Override
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);

        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

}
