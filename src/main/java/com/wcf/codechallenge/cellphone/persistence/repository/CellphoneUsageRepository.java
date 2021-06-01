package com.wcf.codechallenge.cellphone.persistence.repository;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CellphoneUsageRepository extends JpaRepository<CellphoneUsageEntity, Long> {
    @Query(value = "SELECT cpu FROM CellphoneUsageEntity cpu WHERE employeeId = :employeeId AND usageDate = :usageDate")
    CellphoneUsageEntity findByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("usageDate") LocalDate usageDate);
}
