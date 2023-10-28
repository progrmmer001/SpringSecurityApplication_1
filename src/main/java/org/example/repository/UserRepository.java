package org.example.repository;

import org.example.model.AuthUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(AuthUser authUser) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into authUser(username,password,role) values (?,?,?)";
        jdbcTemplate.update(sql, authUser.getUsername(), authUser.getPassword(), authUser.getRole());
    }

    public void delete(Long id) {
        String sql = "delete from authUser where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void delete(String username) {
        String sql = "delete from authUser where username=?";
        jdbcTemplate.update(sql, username);
    }

    public AuthUser findById(Integer id) {
        String sql = "select * from authUser where id=?";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        return jdbcTemplate.queryForObject(sql, beanPropertyRowMapper, id);
    }

    public AuthUser findByName(String name) {
        String sql = "select * from authUser where username=?";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        try {
            return jdbcTemplate.queryForObject(sql, beanPropertyRowMapper, name);
        } catch (Exception e) {
            return null;
        }
    }

    public List<AuthUser> getAll() {
        String sql = "select * from authUser";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        return jdbcTemplate.query(sql, beanPropertyRowMapper);
    }
}
