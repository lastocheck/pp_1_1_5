package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp_start";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Session getSession() {

        if (sessionFactory == null) {
            var properties = new Properties();
            properties.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(AvailableSettings.URL, URL);
            properties.put(AvailableSettings.USER, USERNAME);
            properties.put(AvailableSettings.PASS, PASSWORD);
            properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(AvailableSettings.SHOW_SQL, "true");
            properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(AvailableSettings.HBM2DDL_AUTO, "none");

            var configuration = new Configuration();
            configuration.setProperties(properties);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            sessionFactory = configuration.buildSessionFactory();

        }

        return sessionFactory.openSession();
    }

}
