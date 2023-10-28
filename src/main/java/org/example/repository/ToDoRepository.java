package org.example.repository;

import org.example.model.ToDo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ToDoRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ToDoRepository(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void add(ToDo toDo) {
        String sql;
        if (Objects.nonNull(toDo.getId())) {
            sql = "insert into ToDos(id,title,priority,createdAt) values(:id,:title,:priority,:createdAt)";
        }
        else {
            sql = "insert into ToDos(title,priority,createdAt) values(:title,:priority,:createdAt)";
        }
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(toDo);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public List<ToDo> getAllToDo() {
        String sql = "select * from ToDos order by id";
        BeanPropertyRowMapper<ToDo> beanPropertyRowMapper = new BeanPropertyRowMapper<>(ToDo.class);
        return namedParameterJdbcTemplate.query(sql, beanPropertyRowMapper);
    }

    public void delete(Long id) {
        String sql = "delete from ToDos where id=:id";
        Map<String, Long> id1 = Map.of("id", id);
        namedParameterJdbcTemplate.update(sql, id1);
    }

    public void update(ToDo toDo) {
        String sql = "update ToDos set title=:title,priority=:priority,createdAt=:createdAt where id=:id;";
        SqlParameterSource source = new BeanPropertySqlParameterSource(toDo);
        namedParameterJdbcTemplate.update(sql, source);
    }

    public ToDo getToDo(Long id) {
        String sql = "select * from ToDos where id =:id";
        Map<String, Long> id1 = Map.of("id", id);
        BeanPropertyRowMapper<ToDo> rowMapper = BeanPropertyRowMapper.newInstance(ToDo.class);
        return namedParameterJdbcTemplate.queryForObject(sql, id1, rowMapper);
    }
}
