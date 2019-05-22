package com.geekhub.finalwork.service.orders.rowMapper;

import com.geekhub.finalwork.service.orders.dto.OrderCustomerWorker;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderCustomerWorkerRowMapper implements RowMapper<OrderCustomerWorker> {

    @Override
    public OrderCustomerWorker mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderCustomerWorker ocw = new OrderCustomerWorker();
        ocw.setId(rs.getInt("id"));
        ocw.setTitleOfOrder(rs.getString("title"));
        ocw.setIdWorker(rs.getInt("id_worker"));
        ocw.setFirstName(rs.getString("first_name"));
        ocw.setLastName(rs.getString("last_name"));
        ocw.setIdCustomer(rs.getInt("id_customer"));
        ocw.setTitleOfCustomer(rs.getString("title_of_customer"));
        ocw.setIdInstrument(rs.getInt("id_instrument"));
        ocw.setTitleOfInstrument(rs.getString("title_of_instrument"));
        ocw.setIdMaterial(rs.getInt("id_material"));
        ocw.setTitleOfMaterial(rs.getString("title_of_material"));
        ocw.setDate(rs.getDate("date_of_order"));
        ocw.setAddress(rs.getString("address"));
        ocw.setPrice(rs.getDouble("price"));
        ocw.setDone(rs.getBoolean("done"));
        return ocw;
    }
}