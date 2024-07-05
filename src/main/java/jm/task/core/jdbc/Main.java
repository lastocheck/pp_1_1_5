package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        List<User> users = List.of(
                new User("Antuan", "Petrov", (byte) 12),
                new User("Thom", "Yorke", (byte) 50),
                new User("Francis", "Ngannou", (byte) 35),
                new User("Marie", "Curie", (byte) 100)
        );
        for (var user: users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем — %s добавлен в базу данных%n", user.getName());
        }

        List<User> usersFromDb = userService.getAllUsers();
        usersFromDb.forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
        userService.dropUsersTable();
    }
}
