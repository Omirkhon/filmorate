package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film findById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validate(film);
        findById(film.getId());
        return filmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("Пользоветель не найден"));
        Film film = findById(filmId);

        film.addLike(userId);
    }

    public void deleteLike(int filmId, int userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("Пользоветель не найден"));
        Film film = findById(filmId);

        film.deleteLike(userId);
    }

    public List<Film> findAllPopular(int count) {
        return filmStorage.getFilms()
                .values()
                .stream()
                .sorted((film1, film2) -> film2.getNumOfLikes() - film1.getNumOfLikes())
                .limit(count)
                .toList();
    }

    public void validate(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Некорректная дата релиза.");
        }
    }
}
