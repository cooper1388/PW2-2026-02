package hn.uth.monitordesuscripciones.database;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class DatabaseBootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer.initialize();
    }
}


