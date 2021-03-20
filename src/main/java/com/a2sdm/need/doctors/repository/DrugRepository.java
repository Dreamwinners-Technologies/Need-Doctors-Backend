package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.model.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<DrugModel, String> {

    Optional<List<DrugModel>> findAllByGenericContaining(String genericName);

    Optional<List<DrugModel>> findDistinctByGenericContaining(String genericName);

}
