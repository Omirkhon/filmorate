package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
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
    private int uniqueId = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validate(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        log.debug("Пользователь добавлен");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException();
        }
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
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        }
    }
}
