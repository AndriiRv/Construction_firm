package com.geekhub.finalwork.service.orders;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.dto.OrderCustomerWorker;
import com.geekhub.finalwork.service.orders.dto.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    void saveOrder(Order order, String title, String username) {
        orderRepository.saveOrder(order, title, username);
    }

    Order getOrderByTitle(String title) {
        return orderRepository.getOrderByTitle(title);
    }

    List<Order> getTemplateOrder() {
        return orderRepository.getTemplateOrder();
    }

    public List<Order> getAcceptedOrderByUsername(int id) {
        return orderRepository.getAcceptedOrderByUsername(id);
    }

    public void checkDoneOrder(int id) {
        orderRepository.checkDoneOrder(id);
    }

    public void checkUnDoneOrder(int id) {
        orderRepository.checkUnDoneOrder(id);
    }

    public void updateInstrument(int idOrder, int idInstrument) {
        orderRepository.updateInstrument(idOrder, idInstrument);
    }

    public void updateMaterial(int idOrder, int idMaterial) {
        orderRepository.updateMaterial(idOrder, idMaterial);
    }

    public void updateWorker(int idOrder, int idUnNecessaryWorker, int idWorker) {
        orderRepository.updateWorker(idOrder, idUnNecessaryWorker, idWorker);
    }

    public void removeOrder(int id, int idWorker) {
        orderRepository.removeOrder(id, idWorker);
    }

    public List<OrderCustomerWorker> getOrder() {
        return orderRepository.getOrder();
    }

    public List<Worker> getBusyWorker() {
        return orderRepository.getBusyWorker();
    }

    public List<Worker> getNotBusyWorker() {
        return orderRepository.getNotBusyWorker();
    }

    public List<Worker> getAllWorker() {
        return orderRepository.getAllWorker();
    }

    public List<User> getAllCustomer() {
        return orderRepository.getAllCustomer();
    }

    public List<Order> getAllInstrument() {
        return orderRepository.getAllInstrument();
    }

    public List<Order> getAllMaterial() {
        return orderRepository.getAllMaterial();
    }
}