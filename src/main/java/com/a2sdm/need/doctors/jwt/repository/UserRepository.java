package com.a2sdm.need.doctors.jwt.repository;



import com.a2sdm.need.doctors.jwt.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    boolean existsByPhoneNo(String phoneNo);


    Optional<UserModel> findByPhoneNo(String phoneNo);

    Optional<UserModel> findByUsername(String username);
}