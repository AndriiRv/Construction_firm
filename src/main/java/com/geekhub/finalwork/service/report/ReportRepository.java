package com.geekhub.finalwork.service.report;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.authentication.user.UserRowMapper;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.dto.Worker;
import com.geekhub.finalwork.service.orders.rowMapper.*;
import com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper.ReportInstrumentRowMapper;
import com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper.ReportMaterialRowMapper;
import com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper.ReportOrderRowMapper;
import com.geekhub.finalwork.service.orders.rowMapper.reportRowMapper.ReportUserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReportRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final WorkerRowMapper workerRowMapper;
    private final InstrumentRowMapper instrumentRowMapper;
    private final MaterialRowMapper materialRowMapper;
    private final OrderRowMapper orderRowMapper;
    private final UserRowMapper userRowMapper;
    private final ReportInstrumentRowMapper reportInstrumentRowMapper;
    private final ReportMaterialRowMapper reportMaterialRowMapper;
    private final ReportUserRowMapper reportUserRowMapper;
    private final ReportOrderRowMapper reportOrderRowMapper;

    @Autowired
    public ReportRepository(NamedParameterJdbcTemplate jdbcTemplate,
                            WorkerRowMapper workerRowMapper,
                            InstrumentRowMapper instrumentRowMapper,
                            MaterialRowMapper materialRowMapper,
                            OrderRowMapper orderRowMapper,
                            UserRowMapper userRowMapper,
                            ReportInstrumentRowMapper reportInstrumentRowMapper,
                            ReportMaterialRowMapper reportMaterialRowMapper,
                            ReportUserRowMapper reportUserRowMapper,
                            ReportOrderRowMapper reportOrderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.workerRowMapper = workerRowMapper;
        this.instrumentRowMapper = instrumentRowMapper;
        this.materialRowMapper = materialRowMapper;
        this.orderRowMapper = orderRowMapper;
        this.userRowMapper = userRowMapper;
        this.reportInstrumentRowMapper = reportInstrumentRowMapper;
        this.reportMaterialRowMapper = reportMaterialRowMapper;
        this.reportUserRowMapper = reportUserRowMapper;
        this.reportOrderRowMapper = reportOrderRowMapper;
    }

    List<Worker> simpleQueryOnSelect() {
        String sql = "SELECT * FROM worker WHERE first_name LIKE('M%')";
        List<Worker> workers = jdbcTemplate.query(sql, workerRowMapper);
        workers = workers.stream()
                .sorted(Comparator.comparing(Worker::getId))
                .collect(Collectors.toList());
        return workers;
    }

    List<Order> queryOnSelectWithBetweenAnd() {
        String sql = "SELECT * FROM instrument WHERE date_of_purchase BETWEEN '2017-01-01' AND '2019-01-01'";
        List<Order> instruments = jdbcTemplate.query(sql, instrumentRowMapper);
        instruments = instruments.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instruments;
    }

    List<Order> queryOnSelectWithIn() {
        String sql = "SELECT * FROM material WHERE date_of_purchase IN ('2018-02-08', '2018-09-17', '2018-11-22', '2017-07-16')";
        List<Order> materials = jdbcTemplate.query(sql, materialRowMapper);
        materials = materials.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return materials;
    }

    List<Order> queryOnSelectWithAnd() {
        String sql = "SELECT * FROM \"" + "order" + "\" WHERE title LIKE('%building%') AND price >= '12500000'";
        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper);
        orders = orders.stream()
                .sorted(Comparator.comparing(Order::getIdOrder))
                .collect(Collectors.toList());
        return orders;
    }

    List<Order> queryOnSelectWithOr() {
        String sql = "SELECT * FROM material WHERE date_of_purchase >= '2018-02-08' OR purchase_price >= '150000'";
        List<Order> materials = jdbcTemplate.query(sql, materialRowMapper);
        materials = materials.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return materials;
    }

    List<Order> queryOnSelectWithDistinct() {
        String sql = "SELECT DISTINCT o.* FROM \"" + "order" + "\" AS o INNER JOIN provided_order AS po ON po.id_order = o.id";
        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper);
        orders = orders.stream()
                .sorted(Comparator.comparing(Order::getIdOrder))
                .collect(Collectors.toList());
        return orders;
    }

    List<Order> queryOnSelectWithMin() {
        String sql = "SELECT id, title_of_instrument, min(purchase_price) AS purchase_price, date_of_purchase FROM instrument GROUP BY id ORDER BY purchase_price LIMIT 1";
        List<Order> instruments = jdbcTemplate.query(sql, instrumentRowMapper);
        instruments = instruments.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instruments;
    }

    List<Order> queryOnSelectWithSum() {
        String sql = "SELECT sum(purchase_price) AS purchase_price FROM material";
        List<Order> materials = jdbcTemplate.query(sql, reportOrderRowMapper);
        materials = materials.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return materials;
    }

    List<User> queryOnSelectWithCount() {
        String sql = "SELECT count(id) AS id FROM customer";
        List<User> customers = jdbcTemplate.query(sql, reportUserRowMapper);
        customers = customers.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
        return customers;
    }

    List<Order> queryOnSelectWithMaxAndWhere() {
        String sql = "SELECT id, title_of_instrument, max(purchase_price) AS purchase_price, date_of_purchase " +
                "FROM instrument WHERE date_of_purchase >= '2019-01-07' GROUP BY id ORDER BY purchase_price DESC";
        List<Order> instruments = jdbcTemplate.query(sql, instrumentRowMapper);
        instruments = instruments.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instruments;
    }

    List<Order> queryOnSelectWithLeftJoin() {
        String sql = "SELECT * FROM instrument_order AS io LEFT JOIN instrument AS i ON io.id_instrument = i.id";
        List<Order> instruments = jdbcTemplate.query(sql, reportInstrumentRowMapper);
        instruments = instruments.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instruments;
    }

    List<Order> queryOnSelectWithRightJoin() {
        String sql = "SELECT * FROM instrument_order AS io RIGHT JOIN instrument AS i ON io.id_instrument = i.id";
        List<Order> instruments = jdbcTemplate.query(sql, reportInstrumentRowMapper);
        instruments = instruments.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instruments;
    }

    List<User> queryOnSelectWithInnerJoinAndLike() {
        String sql = "SELECT * FROM customer AS c INNER JOIN users AS u ON c.id_users = u.id WHERE telephone LIKE('097%')";
        List<User> customers = jdbcTemplate.query(sql, userRowMapper);
        customers = customers.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
        return customers;
    }

    List<User> queryOnSelectWithCountAndInnerJoin() {
        String sql = "SELECT count(c.id) AS id FROM customer AS c INNER JOIN users AS u ON c.id_users = u.id WHERE u.role = 'USER'";
        List<User> customers = jdbcTemplate.query(sql, reportUserRowMapper);
        customers = customers.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
        return customers;
    }

    List<Order> queryOnSelectWithCountAndInnerJoinAndHaving() {
        String sql = "SELECT count(m.id_material) AS count_material, po.*, o.* FROM provided_order AS po " +
                "INNER JOIN material_order AS m ON po.id = m.id_provided_order INNER JOIN \"" + "order" + "\" AS o ON po.id_order = o.id GROUP BY po.id, o.id";
        List<Order> customers = jdbcTemplate.query(sql, reportMaterialRowMapper);
        customers = customers.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return customers;
    }
}