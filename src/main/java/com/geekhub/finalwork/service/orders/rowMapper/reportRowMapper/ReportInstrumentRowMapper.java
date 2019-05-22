package com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper;

import com.geekhub.finalwork.service.orders.dto.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReportInstrumentRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setIdOrder(rs.getInt("id_provided_order"));
        order.setTitle(rs.getString("title_of_instrument"));
        order.setPrice(rs.getDouble("purchase_price"));
        order.setDate(rs.getDate("date_of_purchase"));
        return order;
    }
}