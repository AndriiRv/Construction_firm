package com.geekhub.finalwork.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    void saveUser(User user) {
        String sqlInsertToUser = "INSERT INTO users(username, password, role) VALUES(:username, :password, :role)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String role;
        if (checkOnEmptyTableUsers() != 0) {
            role = "USER";
        } else {
            role = "ADMIN";
        }
        SqlParameterSource parameterInsertToUsers = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("role", role);
        jdbcTemplate.update(sqlInsertToUser, parameterInsertToUsers, keyHolder, new String[]{"id"});
        user.setId((int) Objects.requireNonNull(keyHolder.getKey()).longValue());
        int idRole;
        if (checkOnEmptyTableUserRole() != 0) {
            idRole = 2;
        } else {
            idRole = 1;
        }
        String sqlInsertToRole = "INSERT INTO user_role(id_user, id_role) VALUES(:id_user, :id_role)";
        SqlParameterSource parameterInsertToUserRole = new MapSqlParameterSource()
                .addValue("id_user", user.getId())
                .addValue("id_role", idRole);
        jdbcTemplate.update(sqlInsertToRole, parameterInsertToUserRole);
        String sqlInsertToCustomer = "INSERT INTO customer(id_users, first_name, last_name, title_of_customer, address, telephone, email, edrpou_code)" +
                "VALUES(:id_users, :first_name, :last_name, :title_of_customer, :address, :telephone, :email, :edrpou_code)";
        SqlParameterSource parameterInsertToCustomer = new MapSqlParameterSource()
                .addValue("id_users", user.getId())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("title_of_customer", user.getTitle())
                .addValue("address", user.getAddress())
                .addValue("telephone", user.getTelephone())
                .addValue("email", user.getEmail())
                .addValue("edrpou_code", user.getEdrpouCode());
        jdbcTemplate.update(sqlInsertToCustomer, parameterInsertToCustomer);
    }

    void updateUser(User user, String username) {
        String sqlUpdateUsers = "UPDATE users SET password = :password WHERE username = :username";
        SqlParameterSource parameterUpdateUsers = new MapSqlParameterSource()
                .addValue("password", user.getPassword())
                .addValue("username", username);
        jdbcTemplate.update(sqlUpdateUsers, parameterUpdateUsers);
        String sqlUpdateCustomer = "UPDATE customer SET first_name = :first_name, last_name = :last_name, title_of_customer = :title_of_customer, address = :address, telephone = :telephone, email = :email, edrpou_code = :edrpou_code FROM users WHERE users.id = customer.id_users AND username = :username";
        SqlParameterSource parameterUpdateCustomer = new MapSqlParameterSource()
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("title_of_customer", user.getTitle())
                .addValue("address", user.getAddress())
                .addValue("telephone", user.getTelephone())
                .addValue("email", user.getEmail())
                .addValue("edrpou_code", user.getEdrpouCode())
                .addValue("username", username);
        jdbcTemplate.update(sqlUpdateCustomer, parameterUpdateCustomer);
    }

    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT c.*, u.* FROM customer AS c INNER JOIN users AS u ON c.id_users = u.id WHERE u.username = :username";
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("username", username), userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    void doUserIsAdmin(int id) {
        String sqlUpdateUserRole = "UPDATE user_role SET id_role = 1 WHERE id_user = :id";
        jdbcTemplate.update(sqlUpdateUserRole, new MapSqlParameterSource("id", id));
        String sqlUpdateUser = "UPDATE users SET role = 'ADMIN' WHERE id = :id";
        jdbcTemplate.update(sqlUpdateUser, new MapSqlParameterSource("id", id));
    }

    void doUserIsUser(int id) {
        String sqlUpdateUserRole = "UPDATE user_role SET id_role = 2 WHERE id_user = :id";
        jdbcTemplate.update(sqlUpdateUserRole, new MapSqlParameterSource("id", id));
        String sqlUpdateUser = "UPDATE users SET role = 'USER' WHERE id = :id";
        jdbcTemplate.update(sqlUpdateUser, new MapSqlParameterSource("id", id));
    }

    private Integer checkOnEmptyTableUsers() {
        String sql = "SELECT count(*) FROM users";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }

    private Integer checkOnEmptyTableUserRole() {
        String sql = "SELECT count(*) FROM user_role";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }
}