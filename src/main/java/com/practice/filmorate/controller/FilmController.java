package com.practice.filmorate.controller;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.storage.FilmStorage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmStorage filmStorage;
    FilmService filmService;

    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@Valid @RequestBody @PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@Valid @RequestBody @PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> findAllPopular(@Valid @RequestBody @PathVariable int count) {
        return filmService.findAllPopular(count);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmStorage.findAll();
    }

    @DeleteMapping
    public Film delete(@Valid @RequestBody int id) {
        return filmStorage.remove(id);
    }
}
