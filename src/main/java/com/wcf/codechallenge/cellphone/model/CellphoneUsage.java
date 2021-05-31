package com.wcf.codechallenge.cellphone.model;

import com.opencsv.bean.CsvBindByName;

public class CellphoneUsage {
    @CsvBindByName
    private Long employeeId;
    @CsvBindByName
    private String date;
    @CsvBindByName
    private Integer totalMinutes;
    @CsvBindByName
    private Double totalData;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Double getTotalData() {
        return totalData;
    }

    public void setTotalData(Double totalData) {
        this.totalData = totalData;
    }
}
