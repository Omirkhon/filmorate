package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilmDbStorage implements FilmStorage {
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
