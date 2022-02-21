package com.internship.medicines.controllers;

import com.internship.medicines.services.ReportService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Additional controller for reports.
 * JasperReports was used inside the service logic.
 * Usually returns .pdf
 */

@Api(value = "ReportController")
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @SneakyThrows
    @GetMapping
    public String reportFindAll(
            @RequestParam String format,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return reportService.reportFindAll(format, pageable);
    }
}
