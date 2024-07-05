package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String SQL = """
                CREATE TABLE IF NOT EXISTS user
                (
                    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45),
                    last_name varchar(45),
                    age TINYINT
                );             
                """;

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        final String SQL = "DROP TABLE IF EXISTS user";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SQL = """
                INSERT INTO user
                (name, last_name, age)
                values 
                (?, ?, ?);
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        final String SQL = """
                DELETE FROM user WHERE id = ?;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        final String SQL = """
                SELECT * FROM user;
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                Long id = results.getLong("id");
                String name = results.getString("name");
                String lastName = results.getString("last_name");
                Byte age = results.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        final String SQL = "TRUNCATE TABLE user";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
