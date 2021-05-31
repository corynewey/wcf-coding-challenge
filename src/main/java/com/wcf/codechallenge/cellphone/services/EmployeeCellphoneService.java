package com.wcf.codechallenge.cellphone.services;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import org.springframework.ui.Model;

public interface EmployeeCellphoneService {

    CellphoneEntity createCellphoneEntry(CellphoneEntity cellphoneEntity);

    CellphoneUsageEntity createCellphoneUsageEntry(CellphoneUsageEntity cellphoneUsageEntity);

    void generateUsageReport(Model model);
}
