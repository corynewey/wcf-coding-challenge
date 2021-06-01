package com.wcf.codechallenge.cellphone.conrollers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.wcf.codechallenge.cellphone.model.CellphoneUsage;
import com.wcf.codechallenge.cellphone.model.EmployeeCellphone;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import com.wcf.codechallenge.cellphone.services.EmployeeCellphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    private EmployeeCellphoneService employeeCellphoneService;

    @GetMapping(value = "/cell-phone/home")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/cell-phone/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            return "index";
        }

        // parse CSV file to create a list of `EmployeeCellphone` objects
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // create csv bean reader
            CsvToBean<EmployeeCellphone> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(EmployeeCellphone.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of EmployeeCellphone objects
            List<EmployeeCellphone> cellphones = csvToBean.parse();
            for (EmployeeCellphone cellphone : cellphones) {
                CellphoneEntity cellphoneEntity = new CellphoneEntity(cellphone.getEmployeeId(),
                                                                      cellphone.getEmployeeName(),
                                                                      cellphone.getPurchaseDate(),
                                                                      cellphone.getModel());
                employeeCellphoneService.createCellphoneEntry(cellphoneEntity);
            }
        }
        catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }
        return "usage";
    }

    @PostMapping(value = "/cell-phone/upload-usage")
    public String uploadUsage(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            return "usage";
        }

        // Fix the typo in the header. The header name is: emplyeeId but should be: employeeId
        String csvString = new String(file.getBytes(), StandardCharsets.UTF_8);
        csvString = csvString.replace("emplyeeId", "employeeId");
        // parse CSV file to create a list of `CellphoneUsage` objects
        try (Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvString.getBytes(StandardCharsets.UTF_8))))) {
            // create csv bean reader
            CsvToBean<CellphoneUsage> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CellphoneUsage.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of EmployeeCellphone objects
            List<CellphoneUsage> usages = csvToBean.parse();
            for (CellphoneUsage usage : usages) {
                CellphoneUsageEntity usageEntity = new CellphoneUsageEntity(usage.getEmployeeId(),
                                                                           usage.getDate(),
                                                                           usage.getTotalMinutes(),
                                                                           usage.getTotalData());
                employeeCellphoneService.createCellphoneUsageEntry(usageEntity);
            }
            // Generate the report.
            employeeCellphoneService.generateUsageReport(model);
        }
        catch (Exception ex) {
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }
        return "report";
    }
}
