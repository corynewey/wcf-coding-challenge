package com.wcf.codechallenge.cellphone.persistence.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Entity
@Table(name = "CELLPHONE")
public class CellphoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "EMPLOYEEID")
    private Long employeeId;
    @Column(name = "EMPLOYEENAME")
    private String employeeName;
    @Column(name = "PURCHASEDATE")
    private LocalDate purchaseDate;
    @Column(name = "MODEL")
    private String model;

    public CellphoneEntity() {
    }

    public CellphoneEntity(Long employeeId, String employeeName, String purchaseDate, String model) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        // parse the purchase date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.purchaseDate = LocalDate.parse(purchaseDate, formatter);
        this.model = model;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
