package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.model.test.Datum;
import com.a2sdm.need.doctors.model.test.GenericData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<GenericData, String> {
    Optional<GenericData> findByGenericId(int genericId);
}
