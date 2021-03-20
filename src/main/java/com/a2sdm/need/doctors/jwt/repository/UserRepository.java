package com.a2sdm.need.doctors.jwt.repository;



import com.a2sdm.need.doctors.jwt.model.Role;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    boolean existsByPhoneNo(String phoneNo);


    Optional<UserModel> findByPhoneNo(String phoneNo);

    boolean deleteByPhoneNo(String phoneNo);

    Optional<UserModel> findByUsername(String username);

    @Query(value = "select * from user_model where id in (SELECT user_id FROM user_roles " +
            "where role_id = (SELECT id FROM roles where name =:role))", nativeQuery = true)
    List<UserModel> findByRole(String role);


}