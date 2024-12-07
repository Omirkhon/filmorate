package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validate(user);
        if (user.getName().isEmpty() || user.getName().isBlank())
            user.setName(user.getLogin());
        users.put(user.getId(), user);
        log.debug("Пользователь добавлен");
        return user;
    }

    @PutMapping
    public User update(User user) {
        validate(user);
        users.put(user.getId(), user);
        log.debug("Пользователь обновлен");
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.debug("Получение списка пользователей");
        return new ArrayList<>(users.values());
    }

    public void validate(@Valid @RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank())
            throw new ValidationException("Эл. почта не может быть пустой.");
        else if (!user.getEmail().contains("@"))
            throw new ValidationException("Эл. почта должна содержать знак @.");
        else if (user.getLogin().isEmpty() || user.getLogin().isBlank())
            throw new ValidationException("Логин не может быть пустым.");
        else if (user.getLogin().contains(" "))
            throw new ValidationException("Логин не может содержать пробелы.");
        else if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Дата рождения не может быть в будущем.");
    }
}
