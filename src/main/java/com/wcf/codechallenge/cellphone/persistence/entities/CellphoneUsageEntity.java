package com.wcf.codechallenge.cellphone.persistence.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "CELLPHONEUSAGE")
public class CellphoneUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "EMPLOYEEID")
    private Long employeeId;
    @Column(name = "USAGEDATE")
    private LocalDate usageDate;
    @Column(name = "TOTALMINUTES")
    private Integer totalMinutes;
    @Column(name = "TOTALDATA")
    private Double totalData;

    public CellphoneUsageEntity() {
    }

    public CellphoneUsageEntity(Long employeeId, String date, Integer totalMinutes, Double totalData) {
        this.employeeId = employeeId;
        // parse the purchase date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        this.usageDate = LocalDate.parse(date, formatter);
        this.totalMinutes = totalMinutes;
        this.totalData = totalData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate date) {
        this.usageDate = date;
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
