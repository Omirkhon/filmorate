package com.practice.filmorate.model;

import com.practice.filmorate.exceptions.NotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Film {
    int id;
    @NotBlank(message = "Название не может быть пустым.")
    String name;
    @Size(max = 200, message = "Слишком длинное описание.")
    String description;
    LocalDate releaseDate;
    @Positive(message = "Некорректная продолжительность фильма.")
    int duration;
    Set<Integer> likes;
    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(int userId) {
        this.likes.add(userId);
    }

    public void deleteLike(int userId) {
        if (!likes.contains(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        this.likes.remove(userId);
    }

    public Integer getNumOfLikes() {
        return this.likes.size();
    }
}
