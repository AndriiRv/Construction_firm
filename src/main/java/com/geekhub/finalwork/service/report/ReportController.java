package com.geekhub.finalwork.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String getOrderForm(Model model) {
        model.addAttribute("simpleQueryOnSelect", reportService.simpleQueryOnSelect());
        model.addAttribute("queryOnSelectWithBetweenAnd", reportService.queryOnSelectWithBetweenAnd());
        model.addAttribute("queryOnSelectWithIn", reportService.queryOnSelectWithIn());
        model.addAttribute("queryOnSelectWithAnd", reportService.queryOnSelectWithAnd());
        model.addAttribute("queryOnSelectWithOr", reportService.queryOnSelectWithOr());
        model.addAttribute("queryOnSelectWithDistinct", reportService.queryOnSelectWithDistinct());
        model.addAttribute("queryOnSelectWithMin", reportService.queryOnSelectWithMin());
        model.addAttribute("queryOnSelectWithSum", reportService.queryOnSelectWithSum());
        model.addAttribute("queryOnSelectWithCount", reportService.queryOnSelectWithCount());
        model.addAttribute("queryOnSelectWithMaxAndWhere", reportService.queryOnSelectWithMaxAndWhere());
        model.addAttribute("queryOnSelectWithLeftJoin", reportService.queryOnSelectWithLeftJoin());
        model.addAttribute("queryOnSelectWithRightJoin", reportService.queryOnSelectWithRightJoin());
        model.addAttribute("queryOnSelectWithInnerJoinAndLike", reportService.queryOnSelectWithInnerJoinAndLike());
        model.addAttribute("queryOnSelectWithCountAndInnerJoin", reportService.queryOnSelectWithCountAndInnerJoin());
        model.addAttribute("queryOnSelectWithCountAndInnerJoinAndHaving", reportService.queryOnSelectWithCountAndInnerJoinAndHaving());
        return "main/report.html";
    }
}