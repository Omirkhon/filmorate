package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    Map<Integer, User> users = new HashMap<>();
    private int uniqueId = 1;

    @Override
    public List<User> findAll() {
        log.debug("Получение списка пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        log.debug("Получение пользователя");
        return Optional.of(users.get(id));
    }

    @Override
    public User create(User user) {
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        log.debug("Пользователь добавлен");
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        log.debug("Пользователь обновлен");
        return user;
    }
}
