package com.wcf.codechallenge.cellphone;

import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneEntity;
import com.wcf.codechallenge.cellphone.persistence.entities.CellphoneUsageEntity;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneRepository;
import com.wcf.codechallenge.cellphone.persistence.repository.CellphoneUsageRepository;
import com.wcf.codechallenge.cellphone.services.EmployeeCellphoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CellphoneApplicationTests {

	@Autowired
	private CellphoneRepository cellphoneRepository;
	@Autowired
	private CellphoneUsageRepository cellphoneUsageRepository;
	@Autowired
	private EmployeeCellphoneService employeeCellphoneService;

	@Test
	void contextLoads() {
	}

	@Test
	void testReportGeneration() {
		// Create the cellphone entries
		cellphoneRepository.save(new CellphoneEntity(1L, "Fred Flintstone", "20191005", "Samsung Note 10+"));
		cellphoneRepository.save(new CellphoneEntity(2L, "Barney Rubble", "20191220", "Apple iPhone 2"));
		// Create the usage entries
		cellphoneUsageRepository.save(new CellphoneUsageEntity(1L, "10/29/2019", 5, 1.5));
		cellphoneUsageRepository.save(new CellphoneUsageEntity(1L, "10/29/2019", 6, 0.75));	// dupe entry
		cellphoneUsageRepository.save(new CellphoneUsageEntity(1L, "11/1/2019", 10, 2.75));
		Model testModel = new Model() {
			private final Map<String, Object> data = new HashMap<>();
			@Override
			public Model addAttribute(String s, Object o) {
				this.data.put(s, o);
				return this;
			}

			@Override
			public Model addAttribute(Object o) {
				return null;
			}

			@Override
			public Model addAllAttributes(Collection<?> collection) {
				return null;
			}

			@Override
			public Model addAllAttributes(Map<String, ?> map) {
				return null;
			}

			@Override
			public Model mergeAttributes(Map<String, ?> map) {
				return null;
			}

			@Override
			public boolean containsAttribute(String s) {
				return false;
			}

			@Override
			public Object getAttribute(String s) {
				return null;
			}

			@Override
			public Map<String, Object> asMap() {
				return this.data;
			}
		};
		employeeCellphoneService.generateUsageReport(testModel);
		Map<String, Object> map = testModel.asMap();
		assertThat(map.get("phoneCount")).isEqualTo(2);
		assertThat(map.get("totalMinutes")).isEqualTo(21);
		assertThat(map.get("totalData")).isEqualTo(5.0);
		assertThat(map.get("averageMinutes")).isEqualTo(7.0);
		assertThat(map.get("averageData")).isEqualTo(5.0 / 3);
	}
}
