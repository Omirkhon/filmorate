package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserStorage userStorage;
    public void addFriend(int userId, int friendId) {
        if (userStorage.findById(userId).isEmpty() || userStorage.findById(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        User user = userStorage.findById(userId).get();
        User friend = userStorage.findById(friendId).get();

        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    public void deleteFriend(int userId, int friendId) {
        if (userStorage.findById(userId).isEmpty() || userStorage.findById(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        User user = userStorage.findById(userId).get();
        User friend = userStorage.findById(friendId).get();

        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
    }

    public List<User> findAllFriends(int userId) {
        if (userStorage.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        User user = userStorage.findById(userId).get();

        return user.getFriends().stream()
                .filter(friend -> userStorage.findById(friend).isPresent())
                .map(friend -> userStorage.findById(friend).get())
                .toList();
    }

    public List<User> findAllCommonFriends(int userId, int friendId) {
        if (userStorage.findById(userId).isEmpty() || userStorage.findById(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        User user = userStorage.findById(userId).get();
        User friend = userStorage.findById(friendId).get();

        return user.getFriends().stream()
                .filter(i -> userStorage.findById(i).isPresent())
                .filter(i -> user.getFriends().contains(i) && friend.getFriends().contains(i))
                .map(i -> userStorage.findById(i).get())
                .toList();
    }
}
