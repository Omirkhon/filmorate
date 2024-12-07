package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
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
    private int uniqueId = 1;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validate(film);
        film.setId(uniqueId++);
        films.put(film.getId(), film);
        log.debug("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException();
        }
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
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Некорректная дата релиза.");
        }
    }
}
