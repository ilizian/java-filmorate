package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @Test
    void addUserNameItsLogin() {
        User user = new User();
        UserController userController = new UserController();
        user.setLogin("test");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(1925, 12, 12));
        userController.addUser(user);
        assertEquals(user.getLogin(), user.getName());
    }
}