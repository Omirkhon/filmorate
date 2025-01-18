package com.practice.filmorate.model;

import com.practice.filmorate.exceptions.NotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    int id;
    @NotBlank(message = "Название не может быть пустым.")
    String name;
    @Size(max = 200, message = "Слишком длинное описание.")
    String description;
    LocalDate releaseDate;
    @Positive(message = "Некорректная продолжительность фильма.")
    int duration;
    Mpa mpa;

    Set<Genre> genres;
    final Set<Integer> likes = new HashSet<>();

    public void addLike(int userId) {
        this.likes.add(userId);
    }

    public void deleteLike(int userId) {
        if (!likes.contains(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        this.likes.remove(userId);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public Integer getNumOfLikes() {
        return this.likes.size();
    }
}
