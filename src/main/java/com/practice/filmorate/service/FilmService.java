package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    public List<Film> findAll() {
        return new ArrayList<>();
    }

    public void addLike(int userId, int filmId) {
        if (filmStorage.findById(filmId).isEmpty() || userStorage.findById(userId).isEmpty()) {
            throw new NotFoundException("Объект не найден");
        }

        Film film = filmStorage.findById(filmId).get();

        film.addLike(userId);
    }

    public void deleteLike(int userId, int filmId) {
        if (filmStorage.findById(filmId).isEmpty() || userStorage.findById(userId).isEmpty()) {
            throw new NotFoundException("Объект не найден");
        }

        Film film = filmStorage.findById(filmId).get();

        film.deleteLike(userId);
    }

    public List<Film> findAllPopular(int count) {
        return filmStorage.getFilms()
                .values()
                .stream()
                .sorted((film1, film2) -> film2.getNumOfLikes() - film1.getNumOfLikes())
                .limit(10)
                .toList();
    }
}
