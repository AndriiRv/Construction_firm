package com.geekhub.finalwork.service.orders.rowMapper;

import com.geekhub.finalwork.authentication.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setTitle(rs.getString("title_of_customer"));
        user.setAddress(rs.getString("address"));
        user.setTelephone(rs.getString("telephone"));
        user.setEmail(rs.getString("email"));
        user.setEdrpouCode(rs.getInt("edrpou_code"));
        user.setRole(rs.getString("role"));
        return user;
    }
}