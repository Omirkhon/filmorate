package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validate(film);
        films.put(film.getId(), film);
        log.debug("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film update(Film film) {
        validate(film);
        films.put(film.getId(), film);
        log.debug("Фильм обновлен");
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("Получение списка фильмов");
        return new ArrayList<>(films.values());
    }

    public void validate(@Valid @RequestBody Film film) {
        if (film.getName() == null || film.getName().isBlank())
            throw new ValidationException("Название не может быть пустым.");
        else if (film.getDescription().length() > 200)
            throw new ValidationException("Слишком длинное описание.");
        else if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28")))
            throw new ValidationException("Некорректная дата релиза.");
        else if (film.getDuration().isNegative())
            throw new ValidationException("Некорректная продолжительность фильма.");
    }
}
