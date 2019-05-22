package com.geekhub.finalwork.service.report;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.dto.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    List<Worker> simpleQueryOnSelect() {
        return reportRepository.simpleQueryOnSelect();
    }

    List<Order> queryOnSelectWithBetweenAnd() {
        return reportRepository.queryOnSelectWithBetweenAnd();
    }

    List<Order> queryOnSelectWithIn() {
        return reportRepository.queryOnSelectWithIn();
    }

    List<Order> queryOnSelectWithAnd() {
        return reportRepository.queryOnSelectWithAnd();
    }

    List<Order> queryOnSelectWithOr() {
        return reportRepository.queryOnSelectWithOr();
    }

    List<Order> queryOnSelectWithDistinct() {
        return reportRepository.queryOnSelectWithDistinct();
    }

    List<Order> queryOnSelectWithMin() {
        return reportRepository.queryOnSelectWithMin();
    }

    List<Order> queryOnSelectWithSum() {
        return reportRepository.queryOnSelectWithSum();
    }

    List<User> queryOnSelectWithCount() {
        return reportRepository.queryOnSelectWithCount();
    }

    List<Order> queryOnSelectWithMaxAndWhere() {
        return reportRepository.queryOnSelectWithMaxAndWhere();
    }

    List<Order> queryOnSelectWithLeftJoin() {
        return reportRepository.queryOnSelectWithLeftJoin();
    }

    List<Order> queryOnSelectWithRightJoin() {
        return reportRepository.queryOnSelectWithRightJoin();
    }

    List<User> queryOnSelectWithInnerJoinAndLike() {
        return reportRepository.queryOnSelectWithInnerJoinAndLike();
    }

    List<User> queryOnSelectWithCountAndInnerJoin() {
        return reportRepository.queryOnSelectWithCountAndInnerJoin();
    }

    List<Order> queryOnSelectWithCountAndInnerJoinAndHaving() {
        return reportRepository.queryOnSelectWithCountAndInnerJoinAndHaving();
    }
}