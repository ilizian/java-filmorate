package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private static Validator validator;
    private static InMemoryUserStorage userStorage;

    @BeforeEach
    void initial() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        userStorage = new InMemoryUserStorage();
    }

    @Test
    void addUserWrongEmailEmpty() {
        User user = new User();
        user.setLogin("test");
        user.setBirthday(LocalDate.of(1925, 12, 12));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertEquals(1, validates.size());
        ConstraintViolation<User> validate = validates.iterator().next();
        assertEquals("Неправильный email", validate.getMessage());
    }

    @Test
    void addUserWrongEmailWithoutDog() {
        User user = new User();
        user.setLogin("test");
        user.setEmail("testtest.ru");
        user.setBirthday(LocalDate.of(1925, 12, 12));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertEquals(1, validates.size());
        ConstraintViolation<User> validate = validates.iterator().next();
        assertEquals("Неправильный email", validate.getMessage());
    }

    @Test
    void addUserWrongLoginEmpty() {
        User user = new User();
        user.setEmail("test@test.ru");
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertEquals(1, validates.size());
        ConstraintViolation<User> validate = validates.iterator().next();
        assertEquals("Неправильный логин", validate.getMessage());
    }

    @Test
    void addUserWrongLoginSpace() {
        User user = new User();
        user.setLogin("test test");
        user.setEmail("test@test.ru");
        final ValidationException e = assertThrows(
                ValidationException.class,
                () -> userStorage.addUser(user));
        assertEquals("Неправильный логин", e.getMessage());
    }

    @Test
    void addUserWrongBirthdayDate() {
        User user = new User();
        user.setLogin("test");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(2050, 12, 12));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertEquals(1, validates.size());
        ConstraintViolation<User> validate = validates.iterator().next();
        assertEquals("Неправильная дата рождения", validate.getMessage());
    }

    @Test
    void addUserNameItsLogin() throws ValidationException {
        User user = new User();
        user.setLogin("test");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(1925, 12, 12));
        userStorage.addUser(user);
        assertEquals(user.getLogin(), user.getName());
    }
}