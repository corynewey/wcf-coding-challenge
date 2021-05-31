package com.wcf.codechallenge.cellphone.services;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneRepository;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service("EmployeeCellphoneService")
@Transactional
public class EmployeeCellphoneServiceImpl implements EmployeeCellphoneService {
    @Autowired
    private CellphoneRepository cellphoneRepository;

    @Autowired
    private CellphoneUsageRepository cellphoneUsageRepository;

    @Override
    public CellphoneEntity createCellphoneEntry(CellphoneEntity cellphoneEntity) {
        return cellphoneRepository.save(cellphoneEntity);
    }

    @Override
    public CellphoneUsageEntity createCellphoneUsageEntry(CellphoneUsageEntity cellphoneUsageEntity) {
        return cellphoneUsageRepository.save(cellphoneUsageEntity);
    }

    @Override
    public void generateUsageReport(Model model) {
        // The directions say that there are duplicate data for some dates. This query eliminates any duplicate.
        // Alternatively, I could've just queried the whole list and used a Set to eliminate duplicates, but this
        // saves a step and saves on memory if there are a ton of entries.
        List<CellphoneUsageEntity> usageList = cellphoneUsageRepository.findUniqueEntities();

        model.addAttribute("runDate", LocalDate.now());
        model.addAttribute("phoneCount", 25);
        model.addAttribute("totalMinutes", 25);
        model.addAttribute("totalData", 25);
        model.addAttribute("averageMinutes", 25);
        model.addAttribute("averageData", 25);
    }
}
