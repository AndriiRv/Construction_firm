package com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper;

import com.geekhub.finalwork.service.orders.dto.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReportMaterialRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setIdMaterial(rs.getInt("count_material"));
        order.setTitle(rs.getString("title"));
        order.setPrice(rs.getDouble("price"));
        order.setDate(rs.getDate("date_of_order"));
        return order;
    }
}