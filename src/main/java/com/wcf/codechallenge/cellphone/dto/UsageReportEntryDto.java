package com.wcf.codechallenge.cellphone.dto;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;

import java.util.List;

public class UsageReportEntryDto {
    List<CellphoneUsageEntity> usageList;
    Integer totalMinutes;
    Double totalData;

    public List<CellphoneUsageEntity> getUsageList() {
        return usageList;
    }

    public void setUsageList(List<CellphoneUsageEntity> usageList) {
        this.usageList = usageList;
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
