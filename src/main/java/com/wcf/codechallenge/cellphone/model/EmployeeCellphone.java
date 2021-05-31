package com.wcf.codechallenge.cellphone.model;

import com.opencsv.bean.CsvBindByName;

public class EmployeeCellphone {
    @CsvBindByName
    private Long employeeId;
    @CsvBindByName
    private String employeeName;
    @CsvBindByName
    private String purchaseDate;
    @CsvBindByName
    private String model;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
