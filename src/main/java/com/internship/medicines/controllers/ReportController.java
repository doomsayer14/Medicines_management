package com.internship.medicines.controllers;

import com.internship.medicines.services.ReportService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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
    public ResponseEntity<HttpStatus> reportFindAll(
            @RequestParam String format, // pdf or html
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        String s = reportService.reportFindAll(format, pageable) + "/reportAll." + format;
        if (new File(s).exists()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
