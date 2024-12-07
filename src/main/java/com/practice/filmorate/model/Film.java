package com.practice.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
