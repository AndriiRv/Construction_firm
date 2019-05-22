package com.geekhub.finalwork.service.orders;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.service.orders.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrderForm(ModelMap modelMap, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("username", user.getUsername());
        model.addAttribute("order", new Order());
        List<Order> templateOrder = orderService.getTemplateOrder();
        model.addAttribute("templateOrderList", templateOrder);
        return "main/makeOrder.html";
    }

    @PostMapping
    public String save(@RequestParam String titleOfOrder, @RequestParam String customerUsername, Order order, Model model) {
        model.addAttribute("order", order);
        orderService.getOrderByTitle(titleOfOrder);
        orderService.saveOrder(order, titleOfOrder, customerUsername);
        return "redirect:/order";
    }
}