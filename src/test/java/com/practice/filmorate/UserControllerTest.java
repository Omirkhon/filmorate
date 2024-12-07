package com.practice.filmorate;

import com.practice.filmorate.controller.UserController;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    UserController userController = new UserController();
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected String validateAndGetFirstMessageTemplate(User user) {
        return validator.validate(user).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }

    @Test
    public void shouldNotAddWhenEmailIsNotInCorrectFormat() {
        User user = new User("aaaa", "aa", "aaa", LocalDate.of(2018, 1, 1));

        String expected = "Некорректный формат эл. почты.";
        String actual = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldNotAddWhenLoginIsBlank() {
        User user = new User("aaaa@gmail.com", " ", "aaa", LocalDate.of(2018, 1, 1));

        String expected = "Логин не может быть пустым.";
        String actual = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldNotAddWhenLoginContainsSpaces() {
        User user = new User("aaaa@gmail.com", "a a a", "aaa", LocalDate.of(2018, 1, 1));

        String expected = "Логин не может содержать пробелы.";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userController.createUser(user));

        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void shouldNotAddWhenBirthdayIsInFuture() {
        User user = new User("aaaa@gmail.com", "aaa", "aaa", LocalDate.of(2025, 1, 1));

        String expected = "Дата рождения не может быть в будущем.";
        String actual = validateAndGetFirstMessageTemplate(user);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddWhenAllCorrect() {
        User user = new User("aaaa@gmail.com", "aaa", "aaa", LocalDate.of(2006, 1, 24));

        assertEquals(user, userController.createUser(user));
    }
}
