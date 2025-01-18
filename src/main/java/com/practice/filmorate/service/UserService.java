package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAll() {
        return userStorage.findAll();
    }
    public User findById(int id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public void addFriend(int userId, int friendId) {
        User user = findById(userId);

        if (findById(friendId) == null) {
            throw new NotFoundException("Друг не найден");
        }

        user.addFriend(friendId);

        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        findById(userId).deleteFriend(friendId);
        if (findById(friendId) == null) {
            throw new NotFoundException("Друг не найден");
        }

        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> findAllFriends(int userId) {
        User user = findById(userId);

        return userStorage.findAllFriends(userId);
    }

    public List<User> findAllCommonFriends(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);

        return user.getFriends().stream()
                .filter(i -> friend.getFriends().contains(i))
                .map(this::findById)
                .toList();
    }

    public User create(User user) {
        validate(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User update(User user) {
        validate(user);
        findById(user.getId());
        return userStorage.update(user);
    }

    private void validate( User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        }
    }
}
