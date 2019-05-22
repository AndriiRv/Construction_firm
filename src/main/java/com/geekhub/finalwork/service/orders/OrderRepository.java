package com.geekhub.finalwork.service.orders;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.authentication.user.UserRepository;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.dto.OrderCustomerWorker;
import com.geekhub.finalwork.service.orders.dto.Worker;
import com.geekhub.finalwork.service.orders.rowMapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;
    private final UserRepository userRepository;
    private final AcceptedExecutionOrderRowMapper acceptedExecutionOrderRowMapper;
    private final WorkerRowMapper workerRowMapper;
    private final MaterialRowMapper materialRowMapper;
    private final InstrumentRowMapper instrumentRowMapper;
    private final CustomerRowMapper customerRowMapper;
    private final OrderCustomerWorkerRowMapper orderCustomerWorkerRowMapper;
    private Worker worker = new Worker();
    private Order order = new Order();

    @Autowired
    public OrderRepository(NamedParameterJdbcTemplate jdbcTemplate,
                           OrderRowMapper orderRowMapper,
                           UserRepository userRepository,
                           AcceptedExecutionOrderRowMapper acceptedExecutionOrderRowMapper,
                           WorkerRowMapper workerRowMapper,
                           MaterialRowMapper materialRowMapper,
                           InstrumentRowMapper instrumentRowMapper,
                           CustomerRowMapper customerRowMapper,
                           OrderCustomerWorkerRowMapper orderCustomerWorkerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
        this.userRepository = userRepository;
        this.acceptedExecutionOrderRowMapper = acceptedExecutionOrderRowMapper;
        this.workerRowMapper = workerRowMapper;
        this.materialRowMapper = materialRowMapper;
        this.instrumentRowMapper = instrumentRowMapper;
        this.customerRowMapper = customerRowMapper;
        this.orderCustomerWorkerRowMapper = orderCustomerWorkerRowMapper;
    }

    void saveOrder(Order order, String title, String username) {
        int counterInstrumentMaterial = getInstrumentAndMaterial(order);
        String sqlInsertToOrder = "INSERT INTO provided_order(id_order, id_customer, id_worker, id_material, id_instrument, date_of_order, address, price)" +
                "VALUES(:id_order, :id_customer, :id_worker, :id_material, :id_instrument, :date_of_order, :address, :price)";
        Integer priceInstrument = jdbcTemplate.queryForObject("SELECT purchase_price FROM instrument WHERE id = :id",
                new MapSqlParameterSource("id", counterInstrumentMaterial),
                Integer.class);
        Integer priceMaterial = jdbcTemplate.queryForObject("SELECT purchase_price FROM material WHERE id = :id",
                new MapSqlParameterSource("id", counterInstrumentMaterial),
                Integer.class);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterProvidedOrder = new MapSqlParameterSource()
                .addValue("id_order", getOrderByTitle(title).getIdOrder())
                .addValue("id_customer", Objects.requireNonNull(userRepository.getUserByUsername(username)).getId())
                .addValue("id_worker", getWorker(order))
                .addValue("id_material", counterInstrumentMaterial)
                .addValue("id_instrument", counterInstrumentMaterial)
                .addValue("date_of_order", LocalDate.now())
                .addValue("address", order.getAddress())
                .addValue("price", getOrderByTitle(title).getPrice() + (priceInstrument * 0.1) + (priceMaterial * 0.1));
        jdbcTemplate.update(sqlInsertToOrder, parameterProvidedOrder, keyHolder, new String[]{"id"});
        order.setId((int) Objects.requireNonNull(keyHolder.getKey()).longValue());
        String sqlInsertToInstrumentOrder = "INSERT INTO instrument_order(id_instrument, id_provided_order) VALUES(:id_instrument, :id_provided_order)";
        SqlParameterSource parameterInsertToInstrumentOrder = new MapSqlParameterSource()
                .addValue("id_instrument", counterInstrumentMaterial)
                .addValue("id_provided_order", order.getId());
        jdbcTemplate.update(sqlInsertToInstrumentOrder, parameterInsertToInstrumentOrder);
        String sqlInsertToMaterialOrder = "INSERT INTO material_order(id_material, id_provided_order) VALUES(:id_material, :id_provided_order)";
        SqlParameterSource parameterInsertToMaterialOrder = new MapSqlParameterSource()
                .addValue("id_material", counterInstrumentMaterial)
                .addValue("id_provided_order", order.getId());
        jdbcTemplate.update(sqlInsertToMaterialOrder, parameterInsertToMaterialOrder);
        String sqlUpdateWorker = "UPDATE worker SET title_of_work = :title_of_work, busy = true WHERE id = :id";
        SqlParameterSource parameterSourceUpdate = new MapSqlParameterSource()
                .addValue("title_of_work", getOrderByTitle(title).getTitle())
                .addValue("id", order.getIdWorker());
        jdbcTemplate.update(sqlUpdateWorker, parameterSourceUpdate);
        String sqlInsertToAcceptedOrder = "INSERT INTO accepted_for_execution_order(id_order, id_customer, title, price, done) VALUES(:id_order, :id_customer, :title, :price, :done)";
        SqlParameterSource parameterAcceptedOrder = new MapSqlParameterSource()
                .addValue("id_order", order.getId())
                .addValue("id_customer", Objects.requireNonNull(userRepository.getUserByUsername(username)).getId())
                .addValue("title", getOrderByTitle(title).getTitle())
                .addValue("price", getOrderByTitle(title).getPrice())
                .addValue("done", false);
        jdbcTemplate.update(sqlInsertToAcceptedOrder, parameterAcceptedOrder);
    }

    Order getOrderByTitle(String title) {
        try {
            String sql = "SELECT * FROM \"" + "order" + "\" WHERE title = :title";
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("title", title), orderRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private int getWorker(Order order) {
        worker.setCounter(worker.getCounter() + 1);
        order.setIdWorker(worker.getCounter());
        return worker.getCounter();
    }

    private int getInstrumentAndMaterial(Order orders) {
        order.setCounter(order.getCounter() + 1);
        orders.setIdWorker(order.getCounter());
        return order.getCounter();
    }

    List<Order> getTemplateOrder() {
        String sql = "SELECT * FROM \"" + "order" + "\"";
        List<Order> orders = jdbcTemplate.query(sql, orderRowMapper);
        orders = orders.stream()
                .sorted(Comparator.comparing(Order::getIdOrder))
                .collect(Collectors.toList());
        return orders;
    }

    private Order getOrderById(int id) {
        try {
            String sql = "SELECT po.*, o.*, eo.* FROM provided_order AS po " +
                    "INNER JOIN \"" + "order" + "\" AS o ON po.id_order = o.id " +
                    "INNER JOIN customer AS c ON po.id_customer = c.id " +
                    "INNER JOIN accepted_for_execution_order AS eo ON eo.id_customer = c.id AND eo.id_order = po.id WHERE eo.id_order = :id_order";
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id_order", id), acceptedExecutionOrderRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    List<Order> getAcceptedOrderByUsername(int id) {
        String sql = "SELECT po.*, o.*, eo.* FROM provided_order AS po " +
                "INNER JOIN \"" + "order" + "\" AS o ON po.id_order = o.id " +
                "INNER JOIN customer AS c ON po.id_customer = c.id " +
                "INNER JOIN accepted_for_execution_order AS eo ON eo.id_customer = c.id AND eo.id_order = po.id WHERE c.id = :id";
        List<Order> orders = jdbcTemplate.query(sql, new MapSqlParameterSource("id", id), acceptedExecutionOrderRowMapper);
        orders = orders.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return orders;
    }

    void checkDoneOrder(int id) {
        String sql = "UPDATE accepted_for_execution_order SET done = true WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    void checkUnDoneOrder(int id) {
        String sql = "UPDATE accepted_for_execution_order SET done = false WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    void updateInstrument(int idOrder, int idInstrument) {
        Integer priceInstrument = jdbcTemplate.queryForObject("SELECT purchase_price FROM instrument WHERE id = :id",
                new MapSqlParameterSource("id", idInstrument),
                Integer.class);
        String sqlUpdateOrder = "UPDATE provided_order SET id_instrument = :id_instrument, price = :price WHERE id = :id";
        SqlParameterSource parameterUpdateOrder = new MapSqlParameterSource()
                .addValue("id_instrument", idInstrument)
                .addValue("price", getOrderById(idOrder).getPrice() + (priceInstrument * 0.1))
                .addValue("id", idOrder);
        jdbcTemplate.update(sqlUpdateOrder, parameterUpdateOrder);
        String sqlUpdateAcceptedOrder = "UPDATE accepted_for_execution_order SET price = :price WHERE id = :id";
        SqlParameterSource parameterUpdateAcceptedOrder = new MapSqlParameterSource()
                .addValue("price", priceInstrument * 0.1)
                .addValue("id", idOrder);
        jdbcTemplate.update(sqlUpdateAcceptedOrder, parameterUpdateAcceptedOrder);
        String sqlInsertToInstrumentOrder = "INSERT INTO instrument_order (id_instrument, id_provided_order) VALUES(:id_instrument, :id_provided_order)";
        SqlParameterSource parameterInsertToInstrumentOrder = new MapSqlParameterSource()
                .addValue("id_instrument", idInstrument)
                .addValue("id_provided_order", idOrder);
        jdbcTemplate.update(sqlInsertToInstrumentOrder, parameterInsertToInstrumentOrder);
    }

    void updateMaterial(int idOrder, int idMaterial) {
        Integer priceMaterial = jdbcTemplate.queryForObject("SELECT purchase_price FROM material WHERE id = :id",
                new MapSqlParameterSource("id", idMaterial),
                Integer.class);
        String sqlUpdateOrder = "UPDATE provided_order SET id_material = :id_material, price = :price WHERE id = :id";
        SqlParameterSource parameterUpdateOrder = new MapSqlParameterSource()
                .addValue("id_material", idMaterial)
                .addValue("price", getOrderById(idOrder).getPrice() + (priceMaterial * 0.1))
                .addValue("id", idOrder);
        jdbcTemplate.update(sqlUpdateOrder, parameterUpdateOrder);
        String sqlUpdateAcceptedOrder = "UPDATE accepted_for_execution_order SET price = :price WHERE id = :id";
        SqlParameterSource parameterUpdateAcceptedOrder = new MapSqlParameterSource()
                .addValue("price", priceMaterial * 0.1)
                .addValue("id", idOrder);
        jdbcTemplate.update(sqlUpdateAcceptedOrder, parameterUpdateAcceptedOrder);
        String sqlInsertToMaterialOrder = "INSERT INTO material_order (id_material, id_provided_order) VALUES(:id_material, :id_provided_order)";
        SqlParameterSource parameterInsertToMaterialOrder = new MapSqlParameterSource()
                .addValue("id_material", idMaterial)
                .addValue("id_provided_order", idOrder);
        jdbcTemplate.update(sqlInsertToMaterialOrder, parameterInsertToMaterialOrder);
    }

    void updateWorker(int idOrder, int idUnNecessaryWorker, int idWorker) {
        String sqlUpdateUnnecessaryWorker = "UPDATE worker SET title_of_work = NULL, busy = false WHERE id = :id";
        jdbcTemplate.update(sqlUpdateUnnecessaryWorker, new MapSqlParameterSource("id", idUnNecessaryWorker));
        String sqlUpdateNewWorker = "UPDATE worker SET title_of_work = :title_of_work, busy = true WHERE id = :id";
        SqlParameterSource parameterUpdateNewWorker = new MapSqlParameterSource()
                .addValue("title_of_work", Objects.requireNonNull(getOrderById(idOrder)).getTitle())
                .addValue("id", idWorker);
        jdbcTemplate.update(sqlUpdateNewWorker, parameterUpdateNewWorker);
        String sqlUpdateOrder = "UPDATE provided_order SET id_worker = :id_worker WHERE id = :id";
        SqlParameterSource parameterUpdateOrder = new MapSqlParameterSource()
                .addValue("id_worker", idWorker)
                .addValue("id", idOrder);
        jdbcTemplate.update(sqlUpdateOrder, parameterUpdateOrder);
    }

    void removeOrder(int id, int idWorker) {
        String sqlDeleteFromAcceptedOrder = "DELETE FROM accepted_for_execution_order AS eo USING provided_order AS po, customer AS c " +
                "WHERE eo.id_order = po.id AND eo.id_customer = c.id AND eo.id = :id";
        jdbcTemplate.update(sqlDeleteFromAcceptedOrder, new MapSqlParameterSource("id", id));
        String sqlDeleteFromInstrumentOrder = "DELETE FROM instrument_order AS io USING provided_order AS po, instrument AS i " +
                "WHERE po.id_order = io.id_provided_order AND io.id_instrument = i.id AND io.id_provided_order = :id";
        jdbcTemplate.update(sqlDeleteFromInstrumentOrder, new MapSqlParameterSource("id", id));
        String sqlDeleteFromMaterialOrder = "DELETE FROM material_order AS mo USING provided_order AS po, material AS m " +
                "WHERE po.id_order = mo.id_provided_order AND po.id = :id";
        jdbcTemplate.update(sqlDeleteFromMaterialOrder, new MapSqlParameterSource("id", id));
        String sqlDeleteFromProvidedOrder = "DELETE FROM provided_order AS po USING \"" + "order" + "\" AS o, worker AS w " +
                "WHERE po.id_order = o.id AND po.id_worker = w.id AND po.id = :id";
        jdbcTemplate.update(sqlDeleteFromProvidedOrder, new MapSqlParameterSource("id", id));
        String sqlUpdateWorker = "UPDATE worker SET title_of_work = NULL, busy = false " +
                "WHERE id = :id";
        jdbcTemplate.update(sqlUpdateWorker, new MapSqlParameterSource("id", idWorker));
        String sqlSelectFromOrder = "SELECT count(*) FROM provided_order";
        Integer countOfEntries = jdbcTemplate.queryForObject(sqlSelectFromOrder, new MapSqlParameterSource(), Integer.class);
        if (countOfEntries == 0) {
            jdbcTemplate.update("UPDATE worker SET title_of_work = NULL, busy = false WHERE true", new MapSqlParameterSource());
        }
    }

    List<OrderCustomerWorker> getOrder() {
        String sql = "SELECT po.*, o.*, eo.*, w.*, c.*, i.*, m.* FROM provided_order AS po " +
                "INNER JOIN \"" + "order" + "\" AS o ON po.id_order = o.id " +
                "INNER JOIN customer AS c ON po.id_customer = c.id " +
                "INNER JOIN accepted_for_execution_order AS eo ON eo.id_order = po.id " +
                "INNER JOIN worker AS w ON po.id_worker = w.id " +
                "INNER JOIN instrument AS i ON po.id_instrument = i.id " +
                "INNER JOIN material AS m ON po.id_material = m.id";
        List<OrderCustomerWorker> orders = jdbcTemplate.query(sql, orderCustomerWorkerRowMapper);
        orders = orders.stream()
                .sorted(Comparator.comparing(OrderCustomerWorker::getId))
                .collect(Collectors.toList());
        return orders;
    }

    List<Worker> getBusyWorker() {
        String sql = "SELECT * FROM worker AS w INNER JOIN provided_order AS po ON w.id = po.id_worker";
        List<Worker> workers = jdbcTemplate.query(sql, workerRowMapper);
        workers = workers.stream()
                .sorted(Comparator.comparing(Worker::getId))
                .collect(Collectors.toList());
        return workers;
    }

    List<Worker> getNotBusyWorker() {
        String sql = "SELECT * FROM worker WHERE busy = false";
        List<Worker> workers = jdbcTemplate.query(sql, workerRowMapper);
        workers = workers.stream()
                .sorted(Comparator.comparing(Worker::getId))
                .collect(Collectors.toList());
        return workers;
    }

    List<Worker> getAllWorker() {
        String sql = "SELECT * FROM worker";
        List<Worker> workers = jdbcTemplate.query(sql, workerRowMapper);
        workers = workers.stream()
                .sorted(Comparator.comparing(Worker::getId))
                .collect(Collectors.toList());
        return workers;
    }

    List<User> getAllCustomer() {
        String sql = "SELECT c.*, u.* FROM customer AS c INNER JOIN users AS u ON c.id_users = u.id";
        List<User> customers = jdbcTemplate.query(sql, customerRowMapper);
        customers = customers.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
        return customers;
    }

    List<Order> getAllInstrument() {
        String sql = "SELECT * FROM instrument";
        List<Order> instrument = jdbcTemplate.query(sql, instrumentRowMapper);
        instrument = instrument.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return instrument;
    }

    List<Order> getAllMaterial() {
        String sql = "SELECT * FROM material";
        List<Order> material = jdbcTemplate.query(sql, materialRowMapper);
        material = material.stream()
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
        return material;
    }
}