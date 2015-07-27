package org.mypackage.dal.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.mypackage.dal.sql.SqlConnectionProvider;

/**
 *
 * @author dev-dp
 */

public class H2SqlConnectionProvider implements SqlConnectionProvider {
    
    private static final String H2DB_DRIVER = "org.h2.Driver";
    private static final String H2DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String H2DB_USERNAME = "";
    private static final String H2DB_PASSWORD = "";

    @Override
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(H2DB_DRIVER);
        
        return DriverManager.getConnection(H2DB_CONNECTION, H2DB_USERNAME, H2DB_PASSWORD);
    }
    
    
}
