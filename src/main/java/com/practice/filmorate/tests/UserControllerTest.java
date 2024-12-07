package com.practice.filmorate.tests;

import com.practice.filmorate.controller.UserController;
import com.practice.filmorate.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    UserController userController = new UserController();

    @Test
    public void shouldNotAddWhenEmailIsBlank() {
        User user = new User(1, "", "aa", "aaa", LocalDate.of(2018, 1, 1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));
        assertEquals("Эл. почта не может быть пустой.", exception.getMessage());
    }

    @Test
    public void shouldNotAddWhenEmailIsNotInCorrectFormat() {
        User user = new User(1, "aaaa", "aa", "aaa", LocalDate.of(2018, 1, 1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));
        assertEquals("Эл. почта должна содержать знак @.", exception.getMessage());
    }

    @Test
    public void shouldNotAddWhenLoginIsBlank() {
        User user = new User(1, "aaaa@gmail.com", " ", "aaa", LocalDate.of(2018, 1, 1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));
        assertEquals("Логин не может быть пустым.", exception.getMessage());
    }

    @Test
    public void shouldNotAddWhenLoginContainsSpaces() {
        User user = new User(1, "aaaa@gmail.com", "a a a", "aaa", LocalDate.of(2018, 1, 1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));
        assertEquals("Логин не может содержать пробелы.", exception.getMessage());
    }

    @Test
    public void shouldNotAddWhenBirthdayIsInFuture() {
        User user = new User(1, "aaaa@gmail.com", "aaa", "aaa", LocalDate.of(2025, 1, 1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));
        assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }
}
