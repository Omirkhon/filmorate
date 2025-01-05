package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = "select * from genres";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String name = sqlRowSet.getString("name");

            genres.add(new Genre(id, name));
        }
        return genres;
    }

    @Override
    public Optional<Genre> findById(int id) {
        String sql = "select * from genres where id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String name = sqlRowSet.getString("name");

            Genre genre = new Genre(id, name);
            return Optional.of(genre);
        }
        return Optional.empty();
    }
}
