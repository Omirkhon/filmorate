package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String email = sqlRowSet.getString("email");
            String login = sqlRowSet.getString("login");
            String name = sqlRowSet.getString("name");
            Date birthday = sqlRowSet.getDate("birthday");

            LocalDate theBirthday = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDay());

            users.add(new User(id, email, login, name, theBirthday));
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "select * from mpa where id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String email = sqlRowSet.getString("email");
            String login = sqlRowSet.getString("login");
            String name = sqlRowSet.getString("name");
            Date birthday = sqlRowSet.getDate("birthday");

            LocalDate theBirthday = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDay());

            User user = new User(id, email, login, name, theBirthday);

            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
        int id = insert.executeAndReturnKey(map).intValue();
        user.setId(id);

        return user;
    }

    @Override
    public User update(User user) {
        return user;
    }
}
