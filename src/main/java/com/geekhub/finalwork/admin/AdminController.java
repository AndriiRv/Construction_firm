package com.geekhub.finalwork.admin;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.authentication.user.UserService;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.dto.OrderCustomerWorker;
import com.geekhub.finalwork.service.orders.OrderService;
import com.geekhub.finalwork.service.orders.dto.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(ModelMap model) {
        List<OrderCustomerWorker> orders = orderService.getOrder();
        model.addAttribute("orderList", orders);
        List<Worker> busyWorker = orderService.getBusyWorker();
        model.addAttribute("busyWorkerList", busyWorker);
        List<Worker> worker = orderService.getNotBusyWorker();
        model.addAttribute("workerList", worker);
        List<Order> instruments = orderService.getAllInstrument();
        model.addAttribute("instrumentList", instruments);
        List<Order> material = orderService.getAllMaterial();
        model.addAttribute("materialList", material);
        List<User> customers = orderService.getAllCustomer();
        model.addAttribute("customerList", customers);
        return "admin/adminPage.html";
    }

    @RequestMapping(value = "/admin/doDelete/idOrder={id}/idWorker={idWorker}", method = RequestMethod.GET)
    public String deleteElement(@PathVariable int id, @PathVariable int idWorker) {
        orderService.removeOrder(id, idWorker);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/doDone/idOrder={id}", method = RequestMethod.GET)
    public String doDoneOrder(@PathVariable int id) {
        orderService.checkDoneOrder(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/doUnDone/idOrder={id}", method = RequestMethod.GET)
    public String doUnDoneOrder(@PathVariable int id) {
        orderService.checkUnDoneOrder(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/doUserIsAdmin/idUser={id}", method = RequestMethod.GET)
    public String doUserIsAdmin(@PathVariable int id) {
        userService.doUserIsAdmin(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/doUserIsUser/idUser={id}", method = RequestMethod.GET)
    public String doUserIsUser(@PathVariable int id) {
        userService.doUserIsUser(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/UpdateWorker", method = RequestMethod.POST)
    public String doPostWorker(@RequestParam int orderToWorker, @RequestParam int idUnNecessaryWorker, @RequestParam int idWorker) {
        orderService.updateWorker(orderToWorker, idUnNecessaryWorker, idWorker);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/UpdateInstrument", method = RequestMethod.POST)
    public String doPostInstrument(@RequestParam int orderToInstrument, @RequestParam int idInstrument) {
        orderService.updateInstrument(orderToInstrument, idInstrument);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/UpdateMaterial", method = RequestMethod.POST)
    public String doPostMaterial(@RequestParam int orderToMaterial, @RequestParam int idMaterial) {
        orderService.updateMaterial(orderToMaterial, idMaterial);
        return "redirect:/admin";
    }
}