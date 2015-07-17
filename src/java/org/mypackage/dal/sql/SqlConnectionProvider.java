package org.mypackage.dal.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dpa
 */
public interface SqlConnectionProvider {

    Connection createConnection() throws ClassNotFoundException, SQLException;
}
