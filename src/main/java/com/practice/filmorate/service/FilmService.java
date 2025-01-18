package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.GenreStorage;
import com.practice.filmorate.storage.MpaStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public Film findById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден."));
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        if (film.getGenres() != null) {
            film.setGenres(new TreeSet<>(film.getGenres()));
        }
        validate(film);
        List<Genre> genres = genreStorage.findAll();
        for (Genre genre : film.getGenres()) {
            if (!genres.contains(genre)) {
                throw new ValidationException("Такого жанра нет");
            }
        }
        mpaStorage.findById(film.getMpa().getId());
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validate(film);
        findById(film.getId());
        return filmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> findAllPopular(int count) {
        return filmStorage.findAll()
                .stream()
                .sorted((film1, film2) -> film2.getNumOfLikes() - film1.getNumOfLikes())
                .limit(count)
                .toList();
    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Некорректная дата релиза.");
        }
    }
}
