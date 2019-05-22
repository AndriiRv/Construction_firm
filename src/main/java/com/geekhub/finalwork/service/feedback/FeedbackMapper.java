package com.geekhub.finalwork.service.feedback;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedbackMapper implements RowMapper<Feedback> {

    @Override
    public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(rs.getInt("id"));
        feedback.setName(rs.getString("name"));
        feedback.setText(rs.getString("text"));
        feedback.setDate(rs.getDate("date"));
        feedback.setRank(rs.getInt("rank"));
        return feedback;
    }
}