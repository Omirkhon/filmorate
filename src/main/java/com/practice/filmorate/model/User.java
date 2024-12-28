package com.practice.filmorate.model;

import com.practice.filmorate.exceptions.NotFoundException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class User {
    int id;
    @Email (message = "Некорректный формат эл. почты.")
    String email;
    @NotBlank (message = "Логин не может быть пустым.")
    String login;
    String name;
    @PastOrPresent (message = "Дата рождения не может быть в будущем.")
    LocalDate birthday;
    Set<Integer> friends;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriend(int friend) {
        this.friends.add(friend);
    }

    public void deleteFriend(int friend) {
        if (!friends.contains(friend)) {
            throw new NotFoundException("Пользователь не найден");
        }
        this.friends.remove(friend);
    }
}
