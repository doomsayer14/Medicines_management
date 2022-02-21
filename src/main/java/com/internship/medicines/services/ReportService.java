package com.internship.medicines.services;

import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.entities.Medicine;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for reports about {@link Medicine}.
 * Contains business logic realization needed for {@link com.internship.medicines.controllers.ReportController}.
 */

@Service
public class ReportService {

    @Autowired
    MedicineDao medicineDao;

    private final static String PATH = "/Users/vyesman/IdeaProjects/medicines/src/main/resources/reports";

    @SneakyThrows
    public String reportFindAll(String reportFormat, Pageable pageable) {
        Page<Medicine> medicinePage = medicineDao.findAll(pageable);
        //load file and compile
        File file = ResourceUtils.getFile("classpath:MedicineReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        //FontsОпределите используемый шрифт
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Arial_Normal");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(medicinePage.getContent());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Viktor Yesman");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, PATH + "/reportAll.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, PATH + "/reportAll.pdf");
        }
        return "Reports generated in " + PATH;
    }
}
