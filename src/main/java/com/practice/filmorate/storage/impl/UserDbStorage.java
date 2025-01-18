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
            LocalDate birthday = sqlRowSet.getDate("birthday").toLocalDate();

            users.add(new User(id, email, login, name, birthday));
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "select * from users where id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            String email = sqlRowSet.getString("email");
            String login = sqlRowSet.getString("login");
            String name = sqlRowSet.getString("name");
            LocalDate birthday = sqlRowSet.getDate("birthday").toLocalDate();

            User user = new User(id, email, login, name, birthday);

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
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";

        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());

        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_friends");

        Map<String, Object> map = Map.of(
                "user_id", userId,
                "friend_id", friendId
        );

        insert.execute(map);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sql = "delete from user_friends where user_id = ? and friend_id = ?";

        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> findAllFriends(int userId) {
        return new ArrayList<>();
    }
}
