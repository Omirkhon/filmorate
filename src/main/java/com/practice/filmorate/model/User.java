package com.practice.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    int id;
    @Email (message = "Некорректный формат эл. почты.")
    String email;
    @NotBlank (message = "Логин не может быть пустым.")
    String login;
    String name;
    @PastOrPresent (message = "Дата рождения не может быть в будущем.")
    LocalDate birthday;

    final Set<Integer> friends = new HashSet<>();

    public void addFriend(int friend) {
        this.friends.add(friend);
    }

    public void deleteFriend(int friend) {
        this.friends.remove(friend);
    }
}
