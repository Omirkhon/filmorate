package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {

    List<Film> findAll();

    Optional<Film> findById(int id);

    Film create(Film film);

    Film update(Film film);
}
