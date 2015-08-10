package org.mypackage.dal.mysql;

import org.mypackage.dal.ContactRepository;
import org.mypackage.dal.RepositoryFactory;

/**
 *
 * @author dpa
 */
public class MySqlRepositoryFactory implements RepositoryFactory {

    @Override
    public ContactRepository createContactRepository() {
        return new MysqlContactRepository(new MySqlConnectionProvider());
    }

}
