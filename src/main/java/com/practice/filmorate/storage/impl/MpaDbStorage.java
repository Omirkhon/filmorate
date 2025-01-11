package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        List<Mpa> mpa = new ArrayList<>();
        String sql = "select * from mpa";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");

            mpa.add(new Mpa(id, name, description));
        }
        return mpa;
    }

    @Override
    public Optional<Mpa> findById(int id) {
        String sql = "select * from mpa where id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");

            Mpa mpa = new Mpa(id, name, description);
            return Optional.of(mpa);
        }
        return Optional.empty();
    }
}
