package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.GenreStorage;
import com.practice.filmorate.storage.MpaStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class FilmDbStorage implements FilmStorage {
    JdbcTemplate jdbcTemplate;
    MpaStorage mpaStorage;
    GenreStorage genreStorage;


    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String sql = """
                select * from films f
                join films_genres fg on f.id = fg.film_id
                """;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");
            Date releaseDate = sqlRowSet.getDate("birthday");
            int duration = sqlRowSet.getInt("duration");
            int mpaID = sqlRowSet.getInt("mpa_id");
            int genreID = sqlRowSet.getInt("genre_id");

            Mpa mpa = mpaStorage.findById(mpaID).orElseThrow(() -> new NotFoundException("МПА не найден"));
            Genre genre = genreStorage.findById(genreID).orElseThrow(() -> new NotFoundException("Жанр не найден"));

            LocalDate theReleaseDate = LocalDate.of(releaseDate.getYear(), releaseDate.getMonth(), releaseDate.getDay());

            films.add(new Film(id, name, description, theReleaseDate, duration, mpa));
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = "select * from films where id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");
            Date releaseDate = sqlRowSet.getDate("birthday");
            int duration = sqlRowSet.getInt("duration");
            int mpaID = sqlRowSet.getInt("mpa_id");
            int genreID = sqlRowSet.getInt("genre_id");

            Mpa mpa = mpaStorage.findById(mpaID).orElseThrow(() -> new NotFoundException("МПА не найден"));
            Genre genre = genreStorage.findById(genreID).orElseThrow(() -> new NotFoundException("Жанр не найден"));

            LocalDate theReleaseDate = LocalDate.of(releaseDate.getYear(), releaseDate.getMonth(), releaseDate.getDay());

            Film film = new Film(id, name, description, theReleaseDate, duration, mpa);

            return Optional.of(film);
        }
        return Optional.empty();
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        String sql = "update film set name = ?, description = ?, release_date = ?, duration = ? where id = ?";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());

        return film;
    }

    @Override
    public void remove(int id) {

    }
}
