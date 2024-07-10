package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String query = """
                CREATE TABLE IF NOT EXISTS user
                (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45),
                    last_name varchar(45),
                    age TINYINT
                );             
                """;

            session.createNativeQuery(query).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String query = "DROP TABLE IF EXISTS user";

            session.createNativeQuery(query).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSession()) {
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            result = session.createQuery("from User", User.class).list();

            session.getTransaction().commit();
        }

        return result;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSession()) {
            session.beginTransaction();

            String query = "TRUNCATE TABLE user";

            session.createNativeQuery(query).executeUpdate();

            session.getTransaction().commit();
        }

    }
}
