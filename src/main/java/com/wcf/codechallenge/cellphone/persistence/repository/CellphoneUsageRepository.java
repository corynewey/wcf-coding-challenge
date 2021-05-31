package com.wcf.codechallenge.cellphone.persistence.repository;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CellphoneUsageRepository extends JpaRepository<CellphoneUsageEntity, Long> {
    @Query(value = "SELECT totalMinutes, totalData, DISTINCT employeeId, date FROM CellphoneUsageEntity", nativeQuery = true)
    List<CellphoneUsageEntity> findUniqueEntities();
}
