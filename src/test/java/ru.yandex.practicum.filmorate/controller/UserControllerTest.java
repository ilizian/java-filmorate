package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    @Test
    void addUserWrongEmailEmpty() {
        User user = new User();
        user.setLogin("test");
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    UserController userController = new UserController();
                    userController.addUser(user);
                });
        assertEquals("Ошибка. Электронная почта не может быть пустой и должна содержать символ @",
                e.getMessage());
    }

    @Test
    void addUserWrongEmailWithoutDog() {
        User user = new User();
        user.setLogin("test");
        user.setEmail("testtest.ru");
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    UserController userController = new UserController();
                    userController.addUser(user);
                });
        assertEquals("Ошибка. Электронная почта не может быть пустой и должна содержать символ @",
                e.getMessage());
    }

    @Test
    void addUserWrongLoginEmpty() {
        User user = new User();
        user.setEmail("test@test.ru");
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    UserController userController = new UserController();
                    userController.addUser(user);
                });
        assertEquals("Ошибка. Логин пользователя не должен быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void addUserWrongLoginSpace() {
        User user = new User();
        user.setLogin("test test");
        user.setEmail("test@test.ru");
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    UserController userController = new UserController();
                    userController.addUser(user);
                });
        assertEquals("Ошибка. Логин пользователя не должен быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void addUserNameItsLogin() throws ValidationException {
        User user = new User();
        UserController userController = new UserController();
        user.setLogin("test");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(1925, 12, 12));
        userController.addUser(user);
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    void addUserWrongBirthdayDate() {
        User user = new User();
        user.setLogin("test");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(2050, 12, 12));
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> {
                    UserController userController = new UserController();
                    userController.addUser(user);
                });
        assertEquals("Ошибка. Дата рождения не может быть в будущем", e.getMessage());
    }
}