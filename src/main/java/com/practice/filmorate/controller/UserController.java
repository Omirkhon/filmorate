package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import com.practice.filmorate.storage.UserStorage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@Valid @RequestBody @PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@Valid @RequestBody @PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findAllFriends(@Valid @RequestBody @PathVariable int id) {
        return userService.findAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findAllCommonFriends(@Valid @RequestBody @PathVariable int id, @PathVariable int otherId) {
        return userService.findAllCommonFriends(id, otherId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
}