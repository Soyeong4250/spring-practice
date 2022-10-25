package com.sping.dao;

import com.sping.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> selectAll() {
        String sql = "select * from users";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };

        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public User selectById(String sId) {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };

        return this.jdbcTemplate.queryForObject(sql, rowMapper, sId);
    }

    public void add(User user) {
        String sql = "insert into users(id, name, password) values (?, ?, ?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll() {
        String sql = "delete from users";
        this.jdbcTemplate.update(sql);
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

}