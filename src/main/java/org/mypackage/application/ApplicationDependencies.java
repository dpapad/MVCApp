package org.mypackage.application;

import org.mypackage.dal.RepositoryFactory;
import org.mypackage.dal.mysql.MySqlRepositoryFactory;

/**
 *
 * @author dpa
 */
public final class ApplicationDependencies {
    
    public static final RepositoryFactory REPOSITORY_FACTORY;
    
    static {
        REPOSITORY_FACTORY = new MySqlRepositoryFactory();
    }
    
    private ApplicationDependencies() { }
}
