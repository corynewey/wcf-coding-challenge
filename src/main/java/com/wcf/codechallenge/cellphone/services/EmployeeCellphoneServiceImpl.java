package com.wcf.codechallenge.cellphone.services;

import com.wcf.codechallenge.cellphone.dto.UsageReportEntryDto;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneRepository;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;

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
        // The assignment directions say that the usage data contains duplicate entries. However, my analysis of the
        // data shows that they aren't actually duplicates (not all of the fields hold the same values). Thus, I'm going
        // to treat them not as duplicates but rather as multiple entries for the same date. So I will add up the
        // minutes and data fields for those dates that have multiple entries.
        CellphoneUsageEntity oldEntry =
                cellphoneUsageRepository.findByEmployeeIdAndDate(cellphoneUsageEntity.getEmployeeId(),
                                                                 cellphoneUsageEntity.getUsageDate());
        if (null != oldEntry) {
            oldEntry.setTotalMinutes(cellphoneUsageEntity.getTotalMinutes() + oldEntry.getTotalMinutes());
            oldEntry.setTotalData(cellphoneUsageEntity.getTotalData() + oldEntry.getTotalData());
            // set the object to be saved to be the existing entry so that it will update, not insert the row.
            cellphoneUsageEntity = oldEntry;
        }
        return cellphoneUsageRepository.save(cellphoneUsageEntity);
    }

    @Override
    public void generateUsageReport(Model model) {
        List<CellphoneEntity> phoneList = cellphoneRepository.findAll();

        int grandTotalMinutes = 0;
        double grandTotalData = 0.0;
        double averageMinutes = 0.0, averageData = 0.0;
        if (!phoneList.isEmpty()) {
            // The directions say that there are duplicate data for some dates. However, my analysis tells me that the
            // data isn't duplicated (not all of the fields are the same; the minutes and data usage values are different).
            // Thus, I'm handling the "duplicate" data upon insertion into the database. However, if the data actually
            // were duplicated, I could filter out the duplicates with an over-ridden query method on the usage
            // repository.
            List<CellphoneUsageEntity> usageList = cellphoneUsageRepository.findAll();
            if (!usageList.isEmpty()) {
                // Sort by employeeId
                phoneList.sort(new Comparator<CellphoneEntity>() {
                    @Override
                    public int compare(CellphoneEntity o1, CellphoneEntity o2) {
                        return (int)(o1.getEmployeeId() - o2.getEmployeeId());
                    }
                });
                // Sort the usage data by date.
                usageList.sort(new Comparator<CellphoneUsageEntity>() {
                    @Override
                    public int compare(CellphoneUsageEntity o1, CellphoneUsageEntity o2) {
                        return o1.getUsageDate().compareTo(o2.getUsageDate());
                    }
                });
                // Create a map of lists of employee usages. This will allow me to sort by employee Id and then by date.
                // I could also get the same sort by sorting the usage list by date and employee Id, but I want to add
                // a report entry line that shows each employee's total minutes and data. Doing it this way supports
                // that feature
                Map<Long, UsageReportEntryDto> usageMap = new HashMap<>();
                for (CellphoneUsageEntity usageEntity : usageList) {
                    UsageReportEntryDto entry = usageMap
                            .computeIfAbsent(usageEntity.getEmployeeId(), k -> new UsageReportEntryDto());
                    List<CellphoneUsageEntity> entryUsageList = entry.getUsageList();
                    if (null == entryUsageList) {
                        entryUsageList = new ArrayList<>();
                        entry.setUsageList(entryUsageList);
                    }
                    grandTotalMinutes += usageEntity.getTotalMinutes();
                    grandTotalData += usageEntity.getTotalData();
                    entryUsageList.add(usageEntity);
                }
                averageMinutes = grandTotalMinutes / (double)usageList.size();
                averageData = grandTotalData / usageList.size();

                // Now calculate the total minutes and data usage for each employee.
                for (CellphoneEntity cellphoneEntity : phoneList) {
                    UsageReportEntryDto entry = usageMap.get(cellphoneEntity.getEmployeeId());
                    if (null != entry) {            // make sure we have usage data for this employee
                        Integer employeeMinutes = 0;
                        Double employeeData = 0.0;
                        for (CellphoneUsageEntity usage : entry.getUsageList()) {
                            employeeMinutes += usage.getTotalMinutes();
                            employeeData += usage.getTotalData();
                        }
                        entry.setTotalMinutes(employeeMinutes);
                        entry.setTotalData(employeeData);
                    }
                }
                model.addAttribute("cellphones", phoneList);
                model.addAttribute("usageMap", usageMap);
            }
        }

        model.addAttribute("runDate", LocalDate.now());
        model.addAttribute("phoneCount", phoneList.size());
        model.addAttribute("totalMinutes", grandTotalMinutes);
        model.addAttribute("totalData", grandTotalData);
        model.addAttribute("averageMinutes", averageMinutes);
        model.addAttribute("averageData", averageData);
    }
}
