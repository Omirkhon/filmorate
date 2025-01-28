package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.service.MpaService;
import com.practice.filmorate.storage.FilmStorage;
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
    private final MpaService mpaService;

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

            Mpa mpa = mpaService.findById(mpaID);

            films.add(new Film(id, name, description, releaseDate, duration, mpa, new HashSet<>()));
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = """
                select f.ID            as film_id,
                       f.NAME          as film_name,
                       f.DESCRIPTION   as film_description,
                       f.RELEASE_DATE  as film_release_date,
                       f.DURATION      as film_duration,
                       mpa.ID          as mpa_id,
                       mpa.NAME        as mpa_name,
                       mpa.DESCRIPTION as mpa_description,
                       g.ID            as genre_id,
                       g.NAME          as genre_name
                from films f
                         join mpa on f.mpa_id = mpa.id
                         left join films_genres fg on f.id = fg.film_id
                         left join genres g on g.id = fg.genre_id
                where f.id = ?
                """;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        Film film = null;
        while (sqlRowSet.next()) {
            String name = sqlRowSet.getString("FILM_NAME");
            String description = sqlRowSet.getString("FILM_DESCRIPTION");
            LocalDate releaseDate = sqlRowSet.getDate("FILM_RELEASE_DATE").toLocalDate();
            int duration = sqlRowSet.getInt("FILM_DURATION");
            int mpaID = sqlRowSet.getInt("MPA_ID");
            String mpaName = sqlRowSet.getString("MPA_NAME");
            String mpaDescription = sqlRowSet.getString("MPA_DESCRIPTION");
            Mpa mpa = new Mpa(mpaID, mpaName, mpaDescription);
            int genreID = sqlRowSet.getInt("GENRE_ID"); // 0

            if (genreID == 0) {
                film = new Film(id, name, description, releaseDate, duration, mpa, new HashSet<>());
                break;
            }

            String genreName = sqlRowSet.getString("GENRE_NAME");
            Genre genre = new Genre(genreID, genreName);
            if (film == null) {
                film = new Film(id, name, description, releaseDate, duration, mpa, new HashSet<>());
            }

            film.addGenre(genre);
        }
        return Optional.ofNullable(film);
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

        for (Genre genre : film.getGenres()) {
            String sql = "insert into films_genres(film_id, genre_id) values(?, ?)";
            jdbcTemplate.update(sql, id, genre.getId());
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? where id = ?";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("likes");

        Map<String, Object> map = Map.of(
                "user_id", userId,
                "film_id", filmId
        );

        insert.execute(map);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> findPopular(int count) {
        List<Film> films = new ArrayList<>();
        String sql = """
        select f.* from films f
        left join likes on f.id = likes.film_id
        group by f.id
        order by count(likes.film_id) desc
        limit ?
        """;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, count);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String name = sqlRowSet.getString("name");
            String description = sqlRowSet.getString("description");
            LocalDate releaseDate = sqlRowSet.getDate("release_date").toLocalDate();
            int duration = sqlRowSet.getInt("duration");
            int mpaID = sqlRowSet.getInt("mpa_id");

            Mpa mpa = mpaService.findById(mpaID);

            films.add(new Film(id, name, description, releaseDate, duration, mpa, new HashSet<>()));
        }
        return films;
    }

    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaID = rs.getInt("mpa_id");

        Mpa mpa = mpaService.findById(mpaID);

        return new Film(id, name, description, releaseDate, duration, mpa, new HashSet<>());
    }
}
