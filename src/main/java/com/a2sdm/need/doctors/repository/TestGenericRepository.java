package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.model.TestGenericModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestGenericRepository extends JpaRepository<TestGenericModel, String> {
}
