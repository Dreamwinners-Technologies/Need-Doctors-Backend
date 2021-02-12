package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.model.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<DrugModel, String> {

}
