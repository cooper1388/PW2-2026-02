package hn.uth.appquinielajsf.beans;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CDI.current().select(CsvDataLoader.class).get().inicializarSiNecesario();
    }
}
