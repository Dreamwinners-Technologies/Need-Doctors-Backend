package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.model.TestMedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMedicineRepository extends JpaRepository<TestMedicineModel, String> {
}
