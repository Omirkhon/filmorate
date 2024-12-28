package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    Map<Integer, User> users = new HashMap<>();
    List<User> findAll();

    Optional<User> findById(int id);

    User create(User user);

    User update(User user);

    User remove(int id);
}
