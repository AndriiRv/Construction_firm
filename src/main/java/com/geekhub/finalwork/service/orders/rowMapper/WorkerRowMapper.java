package com.geekhub.finalwork.service.orders.rowMapper;

import com.geekhub.finalwork.service.orders.dto.Worker;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorkerRowMapper implements RowMapper<Worker> {

    @Override
    public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Worker worker = new Worker();
        worker.setId(rs.getInt("id"));
        worker.setFirstName(rs.getString("first_name"));
        worker.setLastName(rs.getString("last_name"));
        worker.setTelephone(rs.getString("telephone"));
        worker.setAddress(rs.getString("address"));
        worker.setEmail(rs.getString("email"));
        worker.setBusy(rs.getBoolean("busy"));
        worker.setTitle(rs.getString("title_of_work"));
        return worker;
    }
}