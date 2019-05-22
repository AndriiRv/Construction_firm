package com.geekhub.finalwork.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FeedbackRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public FeedbackRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void createFeedback(String name, String text, int rank) {
        String sql = "INSERT INTO feedback (name, text, date, rank) VALUES(:name, :text, :date, :rank)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("text", text)
                .addValue("date", LocalDate.now())
                .addValue("rank", rank);
        jdbcTemplate.update(sql, parameterSource);
    }

    List<Feedback> getFeedbackOrderedByDate() {
        String sql = "SELECT * FROM feedback";
        List<Feedback> feedbacks = jdbcTemplate.query(sql, new FeedbackMapper());
        feedbacks = feedbacks.stream()
                .sorted(Comparator.comparing(Feedback::getDate).reversed())
                .collect(Collectors.toList());
        return feedbacks;
    }
}