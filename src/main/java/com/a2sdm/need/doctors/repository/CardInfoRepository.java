package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.model.CardModel;
//import org.kolobok.annotation.FindWithOptionalParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardInfoRepository extends JpaRepository<CardModel, String> {

    Optional<CardModel> findByAddedBy(String userName);

}
