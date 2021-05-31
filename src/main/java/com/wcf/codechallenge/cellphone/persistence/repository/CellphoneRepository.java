package com.wcf.codechallenge.cellphone.persistence.repository;

import com.wcf.codechallenge.cellphone.model.EmployeeCellphone;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellphoneRepository extends JpaRepository<CellphoneEntity, Long> {
}
