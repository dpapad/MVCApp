package org.mypackage.application;

import org.mypackage.controller.ControllerFactory;
import org.mypackage.dal.RepositoryFactory;
import org.mypackage.dal.mysql.MySqlRepositoryFactory;
import org.mypackage.controller.impl.ControllerFactoryImpl;

/**
 *
 * @author dpa
 */
public final class ApplicationDependencies {
    
    public static final RepositoryFactory REPOSITORY_FACTORY;
    public static final ControllerFactory CONTROLLER_FACTORY;
    static {
        REPOSITORY_FACTORY = new MySqlRepositoryFactory();
    }
    
    static {
        CONTROLLER_FACTORY = new ControllerFactoryImpl();
    }   
    
    private ApplicationDependencies() { }
}
