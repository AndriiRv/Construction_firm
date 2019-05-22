package com.geekhub.finalwork.controller;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Controllers {
    private final OrderService orderService;

    @Autowired
    public Controllers(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getWelcomePage() {
        return "index.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getWelcomePage(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("firstName", user.getFirstName());
        modelMap.put("lastName", user.getLastName());
        modelMap.put("username", user.getUsername());
        modelMap.put("role", user.getRole());
        return "main/homePage.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(ModelMap modelMap, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            modelMap.put("error", "Invalid username and/or password.");
        }
        return "login/login.html";
    }

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public String getGalleryPage() {
        return "main/gallery.html";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String getErrorPage() {
        return "main/accessDenied.html";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String getAboutPage() {
        return "main/aboutPage.html";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String getAccountPage(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("username", user.getUsername());
        List<Order> acceptedOrders = orderService.getAcceptedOrderByUsername(user.getId());
        modelMap.addAttribute("acceptedOrderList", acceptedOrders);
        return "main/account.html";
    }
}