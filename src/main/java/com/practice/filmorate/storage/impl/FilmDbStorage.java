package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.GenreStorage;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;


    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String sql = "select * from films";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");
            LocalDate releaseDate = sqlRowSet.getDate("release_date").toLocalDate();
            int duration = sqlRowSet.getInt("duration");
            int mpaID = sqlRowSet.getInt("mpa_id");

            Mpa mpa = mpaStorage.findById(mpaID);

            films.add(new Film(id, name, description, releaseDate, duration, mpa));
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = """
                select * from films f
                join films_genres fg on f.id = fg.film_id
                where f.id = ?
                """;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");
            LocalDate releaseDate = sqlRowSet.getDate("release_date").toLocalDate();
            int duration = sqlRowSet.getInt("duration");
            int mpaID = sqlRowSet.getInt("mpa_id");
            int genreID = sqlRowSet.getInt("genre_id");

            Mpa mpa = mpaStorage.findById(mpaID);
            Genre genre = genreStorage.findById(genreID);

            Film film = new Film(id, name, description, releaseDate, duration, mpa);
            film.addGenre(genre);

            return Optional.of(film);
        }
        return Optional.empty();
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa().getId()
        );
        int id = insert.executeAndReturnKey(map).intValue();
        film.setId(id);

        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? where id = ?";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        return film;
    }

    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaID = rs.getInt("mpa_id");

        Mpa mpa = mpaStorage.findById(mpaID);

        return new Film(id, name, description, releaseDate, duration, mpa);
    }
}
